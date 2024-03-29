package com.ftp.osmserverproj.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class FTPConfiguration {
    @Bean
    public DefaultFtpSessionFactory sf() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("localhost");
        sf.setPort(2121);
        sf.setUsername("anonymous");
        sf.setPassword("");
        return sf;
    }

    @ServiceActivator(inputChannel = "ftpMGET")
    @Bean
    public FtpOutboundGateway getFiles() {
        FtpOutboundGateway gateway = new FtpOutboundGateway(sf(),"mget","payload");
        gateway.setAutoCreateDirectory(Boolean.TRUE);
        gateway.setLocalDirectory(new File("/ftpFolder"));
        gateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
        gateway.setFilter(new AcceptOnceFileListFilter<>());
        gateway.setOutputChannelName("fileResults");

        return gateway;
    }

    @Bean
    public MessageChannel fileResults() {
        DirectChannel channel = new DirectChannel();
        channel.addInterceptor(tap());
        return channel;
    }

    @Bean
    public WireTap tap() {

        return new WireTap("logging");
    }

    @ServiceActivator(inputChannel = "logging")
    @Bean
    public LoggingHandler logger() {
        LoggingHandler logger = new LoggingHandler(LoggingHandler.Level.INFO);
        logger.setLogExpressionString("'Files:' + payload");

        return logger;
    }


}

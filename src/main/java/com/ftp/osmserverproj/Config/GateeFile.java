package com.ftp.osmserverproj.Config;

import org.springframework.integration.annotation.MessagingGateway;

import java.io.File;
import java.util.List;

@MessagingGateway(defaultRequestChannel = "ftpMGET", defaultReplyChannel = "fileResults")

public interface GateeFile {
    List<File> mget(String directory);
}

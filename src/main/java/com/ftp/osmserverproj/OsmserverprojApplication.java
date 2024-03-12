package com.ftp.osmserverproj;
import com.ftp.osmserverproj.Config.GateeFile;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class OsmserverprojApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsmserverprojApplication.class, args);
    }



  /*  @Bean
    public ApplicationRunner runner(GateeFile gateFile){
        return  args -> {
            List<File> files = gateFile.mget(".");
            for(File file : files){
                System.out.println("File => "+file.getAbsolutePath());
            }
        };
    }*/
}

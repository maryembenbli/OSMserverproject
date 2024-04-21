package com.ftp.osmserverproj;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
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

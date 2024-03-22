package org.example;


import org.example.Services.InitializerService;
import org.example.Storage.StorageProperties;
import org.example.Storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @Bean
    CommandLineRunner runner(InitializerService init, StorageService service){
        return args -> {
            service.init();
          init.seedCategories();
          init.seedTags();
          init.seedPosts();
          init.seedPostTagRelations();
        };
    }
}
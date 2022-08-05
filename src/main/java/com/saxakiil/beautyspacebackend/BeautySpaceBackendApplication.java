package com.saxakiil.beautyspacebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-" + "${spring.profiles.active}.yml")
public class BeautySpaceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeautySpaceBackendApplication.class, args);
    }

}

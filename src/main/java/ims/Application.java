package ims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation enables Auto-configuration, Component Scanning, and Extra Configuration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Under the hood, this launches the local Tomcat container (if web starter is added)
        // and initializes the ApplicationContext
        SpringApplication.run(Application.class, args);
    }
}
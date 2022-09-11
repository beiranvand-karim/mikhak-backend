package com.example.transportationbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: 05.09.22 Enter the configs in application.properties base application.example.properties correctly before run
//  the app.
@SpringBootApplication
public class TransportationBackendApplication {
    public static void main(String[] args) {
        // TODO: 05.09.22 create archives tables
        SpringApplication.run(TransportationBackendApplication.class, args);
    }
}

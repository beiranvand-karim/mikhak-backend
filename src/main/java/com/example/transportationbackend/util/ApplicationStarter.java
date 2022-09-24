package com.example.transportationbackend.util;

import com.example.transportationbackend.repositories.ManualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStarter implements ApplicationRunner {

    @Autowired
    private ManualRepository manualRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO: 22.09.22 use flyway db instead that first step
        // TODO: 22.09.22 spring security : insert users data with flyway db
        // TODO: 22.09.22 spring security : authentication across the board
        // TODO: 22.09.22 spring security : migrate to https 
        manualRepository.createTableIfNotExists();
    }
}

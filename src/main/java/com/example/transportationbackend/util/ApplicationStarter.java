package com.example.transportationbackend.util;

import com.example.transportationbackend.repositories.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStarter implements ApplicationRunner {

    @Autowired
    private ArchiveRepository archiveRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        archiveRepository.createTableIfNotExists();
    }
}

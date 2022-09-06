package com.example.transportationbackend.controller;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.repositories.LightPostRepository;
import com.example.transportationbackend.repositories.RoadRepository;
import com.example.transportationbackend.services.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/transportation")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private final String marker = "Controller";

    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private LightPostRepository lpRepository;


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private FileManager fileManager;

    @GetMapping("/all_roads")
    public List<RegisteredRoad> getRoads() {
        return roadRepository.findAll();
    }

    @GetMapping("/lightposts")
    public List<LightPost> getLightPosts() {
        return lpRepository.findAll();
    }

    @PostMapping(value = "/upload_file")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (fileManager.isExcelFile(file)) {
            Path filePath = fileManager.saveFile(file);
            runJob(filePath);
        }
    }

    private void runJob(Path filePath) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("ID", UUID.randomUUID().toString())
                .addString("filePath", filePath.toString())
                .toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException
                | JobRestartException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            logger.error(marker, e.getMessage());
        }
    }
}

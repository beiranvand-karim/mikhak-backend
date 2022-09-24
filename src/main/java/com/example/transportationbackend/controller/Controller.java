package com.example.transportationbackend.controller;

import com.example.transportationbackend.TransportationBackendApplication;
import com.example.transportationbackend.models.lightpost.LightPost;
import com.example.transportationbackend.models.road.RegisteredRoad;
import com.example.transportationbackend.services.DataService;
import com.example.transportationbackend.services.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
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
    private final DataService service;
    private final JobLauncher jobLauncher;
    private final Job job;
    private final FileManager fileManager;

    public Controller(DataService service, JobLauncher jobLauncher, Job job, FileManager fileManager) {
        this.service = service;
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.fileManager = fileManager;
    }

    @GetMapping("/all_roads")
    public List<RegisteredRoad> getRoads() {
        return service.getAllRoads();
    }

    @GetMapping("/lightposts")
    public List<LightPost> getLightPosts() {
        return service.getAllLightPosts();
    }

    @GetMapping("/lightposts/{roadId}")
    public List<LightPost> getLightPosts(@PathVariable double roadId) {
        return service.getLightPostsByRoadId(roadId);
    }

    @PostMapping(value = "/register_road")
    public void registerRoad(@RequestBody RegisteredRoad road) {
        RegisteredRoad roadToSave = road.clone();
        List<LightPost> lpLists = road.getLightPosts()
                .stream()
                .map(lp -> {
                    lp.setRegisteredRoad(roadToSave);
                    return lp.clone();
                })
                .toList();
        roadToSave.setLightPosts(lpLists);
        service.registerRoad(roadToSave);
    }

    @PostMapping(value = "/submit_light_post")
    public void submitLightPost(@RequestBody LightPost lp) {
        LightPost lpToSave = lp.clone();
        service.submitLightPost(lpToSave,
                lp.getRegisteredRoad().getRoadId());
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

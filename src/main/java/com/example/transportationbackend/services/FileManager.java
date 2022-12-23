package com.example.transportationbackend.services;

import com.example.transportationbackend.TransportationBackendApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Component
public class FileManager {

    private static final Logger logger = LoggerFactory.getLogger(TransportationBackendApplication.class);
    private final String marker = "File Manager";
    private File dataFile;
    private File uploadDirectory;
    private File rootDir = null;

    public FileManager() {
        setUpRootDir();
    }

    private void setUpRootDir() {
        try {
            rootDir = new File(this.getClass().getResource("/").toURI());
        } catch (URISyntaxException e) {
            logger.error(marker, e.getMessage());
        }
    }

    public void handleUploadDirectory() {
        this.uploadDirectory = new File(rootDir.getAbsolutePath() + "/uploads");
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    }

    public boolean isExcelFile(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xlsx");
    }

    public Path saveFile(MultipartFile file) throws IOException {
        dataFile = new File(this.uploadDirectory.getAbsolutePath() + "/" + file.getOriginalFilename());
        file.transferTo(dataFile);
        return dataFile.toPath();
    }

    public void deleteAll() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Files.deleteIfExists(dataFile.toPath());
                } catch (Exception e) {
                    logger.error(marker, "delete temporary file error");
                }
            }
        };
    }
}

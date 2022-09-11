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

    public FileManager() {
        handleUploadDirectory();
    }


    public void handleUploadDirectory() {
        File rootDir = null;
        try {
            rootDir = new File(this.getClass().getResource("/").toURI());
        } catch (URISyntaxException e) {
            logger.error(marker, e.getMessage());
        }
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
        try {
            Files.deleteIfExists(this.dataFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

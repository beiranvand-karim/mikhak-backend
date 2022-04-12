package com.example.transportationbackend.services;

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
            e.printStackTrace();
        }
        this.uploadDirectory = new File(rootDir.getAbsolutePath() + "/uploads");

        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    }

    public boolean isExcelFile(MultipartFile file) {
        if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xlsx")) {
            return true;
        }
        return false;
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

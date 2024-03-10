package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository
public class FileRepository {

    private final String _rootDir = "C:/Users/hanna/fileFolder";
    public void writeFile(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(_rootDir));
            var path = Paths.get(_rootDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public void getFile(String name) {
    };

    public void getFileNames() {};

}
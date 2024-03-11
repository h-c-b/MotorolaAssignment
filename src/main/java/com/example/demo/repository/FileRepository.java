package com.example.demo.repository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileRepository {

    private final String _rootDir = "C:/Users/hanna/fileFolder";
    public void writeFile(MultipartFile file) {
        try {
            Files.createDirectories(Path.of(_rootDir));
            var path = Path.of(_rootDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource getFile(String fileName) {
        var path = Path.of(_rootDir, fileName);
        try {
            return new UrlResource(path.toUri());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFileNames() {}

}
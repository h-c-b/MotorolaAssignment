package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Repository
public class FileRepository implements IFileRepository {

    @Value("${fileStoreRoot}")
    private String rootDirPath;
    
    public boolean saveFile(MultipartFile file) throws IOException {
        Files.createDirectories(Path.of(rootDirPath));
        return Files.copy(file.getInputStream(), Path.of(rootDirPath, file.getOriginalFilename())) > 0;
    }

    public Resource getFile(String fileName) throws MalformedURLException {
        return new UrlResource(Path.of(rootDirPath, fileName).toUri());
    }

    public Stream<Path> getFileNames() throws IOException {
        return Files.list(Path.of(rootDirPath));
    }
}
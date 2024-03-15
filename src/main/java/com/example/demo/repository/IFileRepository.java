package com.example.demo.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface IFileRepository {
    boolean saveFile(MultipartFile file) throws IOException;
    Resource getFile(String fileName) throws MalformedURLException;
    Stream<Path> getFileNames() throws IOException;
}

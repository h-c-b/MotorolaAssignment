package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface IFileService {
    List<String> getFileNames() throws IOException;

    boolean uploadFile(MultipartFile file) throws IOException;

    Resource downloadFile(String name) throws MalformedURLException;

    void validateFileName(String name);
}

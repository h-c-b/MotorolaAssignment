package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface IFileController {
    ResponseEntity<String> uploadFile(MultipartFile file) throws IOException;
    ResponseEntity<String> getFileNames() throws IOException;
    ResponseEntity<?> downloadFile(String fileName) throws MalformedURLException, FileNotFoundException;
}

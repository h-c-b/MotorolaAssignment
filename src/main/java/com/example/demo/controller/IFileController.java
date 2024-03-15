package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Controller to handle HTTP operations to save and retrieve files and a list of uploaded file names
 */
public interface IFileController {

    /**
     * Post endpoint to upload a file as a Multipart file
     * @param file the file to be uploaded
     * @return A 200 Response Entity with the file name
     * @throws IOException if an I/O error occurs when writing
     */
    ResponseEntity<String> uploadFile(MultipartFile file) throws IOException;

    /**
     * Retrieve a list of file names
     * @return a 200 Response Entity with all the available file names
     * @throws IOException if an I/O error occurs when reading the names
     */
    ResponseEntity<String> getFileNames() throws IOException;

    /**
     * Retrieve a file by its file name
     * @param fileName tha name of the file
     * @return a 200 Response Entity with a resource containing the file
     * @throws MalformedURLException if the file can't be resolved
     */
    ResponseEntity<Resource> downloadFile(String fileName) throws MalformedURLException;
}

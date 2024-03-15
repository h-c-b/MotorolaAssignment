package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.InvalidPathException;
import java.util.List;

/**
 * Service to handle high level file operations
 */
public interface IFileService {

    /**
     * Retrieve a list of file names in the directory
     * @return the list of file names
     * @throws IOException if an I/O error occurs when opening the directory
     */
    List<String> getFileNames() throws IOException;

    /**
     * Persist the file in the persistence layer
     * @param file file to be persisted
     * @return true on success
     * @throws IOException if an I/O error occurs when writing
     */
    boolean uploadFile(MultipartFile file) throws IOException;

    /**
     * Retrieve a file resource
     * @param name the name of the file
     * @return a Resource containing the requested file
     * @throws MalformedURLException  if the given file's path can't be resolved
     */
    Resource downloadFile(String name) throws MalformedURLException;

    /**
     * Validate the name of a file
     * @param name the file's name
     * @throws InvalidPathException if the name can't be resolved
     */
    void validateFileName(String name) throws InvalidPathException;
}

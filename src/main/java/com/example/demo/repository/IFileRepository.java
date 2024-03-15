package com.example.demo.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.stream.Stream;


/**
 * Simple repository class to handle file operations
 */
public interface IFileRepository {

    /**
     * Save a file
     * @param file the file to save
     * @return true if bytes were written
     * @throws IOException if an I/O error occurs when writing
     */
    boolean saveFile(MultipartFile file) throws IOException;

    /**
     * Retrieve a file
     * @param fileName the name of the file
     * @return the file
     * @throws MalformedURLException if the given file's path can't be resolved
     */
    Resource getFile(String fileName) throws MalformedURLException;

    /**
     * Retrieve a stream of file names in the operating directory
     * @return the stream
     * @throws IOException if an I/O error occurs when opening the directory
     */
    Stream<Path> getFileNames() throws IOException;
}

package com.example.demo.service;

import com.example.demo.repository.IFileRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService implements IFileService {
    private final IFileRepository fileRepository;

    public FileService(IFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<String> getFileNames() throws IOException {
        try (Stream<Path> fileStream = this.fileRepository.getFileNames()) {
            return fileStream.map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
        }
    }

    public boolean uploadFile(MultipartFile file) throws IOException {
        return this.fileRepository.saveFile(file);
    }

    public Resource downloadFile(String fileName) throws MalformedURLException {
        return this.fileRepository.getFile(fileName);
    }

    public void validateFileName(String fileName) throws InvalidPathException {
        Paths.get(fileName);
    }
}
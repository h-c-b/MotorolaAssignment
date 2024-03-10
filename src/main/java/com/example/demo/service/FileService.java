package com.example.demo.service;

import com.example.demo.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private final FileRepository _fileRepository;

    public FileService(FileRepository fileRepository) {
        this._fileRepository = fileRepository;
    }

    public void getFileNames() {
        this._fileRepository.getFileNames();
    }

    public void uploadFile(MultipartFile file) {
        this._fileRepository.writeFile(file);
    }

    public void downloadFile(String name) {
        this._fileRepository.getFile(name);
    }
}
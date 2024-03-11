package com.example.demo.service;

import com.example.demo.repository.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

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

    public Resource downloadFile(String name) throws FileNotFoundException {
        var fileToDownLoad = this._fileRepository.getFile(name);
        if(fileToDownLoad.exists()) {
            return fileToDownLoad;
        } else {
            throw new FileNotFoundException();
        }
    }
}
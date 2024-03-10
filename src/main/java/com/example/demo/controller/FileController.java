package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService _fileService;
    public FileController(FileService fileService) {
        this._fileService = fileService;
    }

    @PostMapping
    public void uploadFiles(@RequestBody MultipartFile file) {
        this._fileService.uploadFile(file);
    }

     @GetMapping
     public void getFileNames() {
     }

     @GetMapping("/{fileName}")
     public void downloadFileByName(@RequestParam String fileName) {
     }
}
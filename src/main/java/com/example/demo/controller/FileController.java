package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

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
     public ResponseEntity<?> downloadFileByName(@PathVariable String fileName) {
        if(fileName.contains(" ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        try {
           Resource fileToDownLoad  = _fileService.downloadFile(fileName);
           return ResponseEntity.status(HttpStatus.FOUND)
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                   .contentType(MediaType.parseMediaType("application/octet-stream"))
                   .body(fileToDownLoad);
        } catch(FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
     }
}
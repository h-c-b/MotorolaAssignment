package com.example.demo.controller;

import com.example.demo.service.IFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController implements IFileController {
    private final IFileService fileService;

    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        this.fileService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
    }

     @GetMapping(produces = "application/json")
     public ResponseEntity<String> getFileNames() throws IOException {
        List<String> fileNames = fileService.getFileNames();
        return ResponseEntity.status(HttpStatus.OK).body(String.join(",", fileNames));
     }

     @GetMapping(value ="/{fileName}", produces = "application/octet-stream")
     public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws MalformedURLException {
           fileService.validateFileName(fileName);
           Resource fileToDownLoad  = fileService.downloadFile(fileName);
           return ResponseEntity.status(HttpStatus.OK)
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;",  "filename=\"" + fileName + "\"")
                   .body(fileToDownLoad);
     }
}
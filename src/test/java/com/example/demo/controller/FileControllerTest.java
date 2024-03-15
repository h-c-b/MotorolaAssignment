package com.example.demo.controller;

import com.example.demo.repository.FileRepository;
import com.example.demo.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileControllerTest {

    private FileService service;
    private FileController controller;
    @BeforeEach
    public void setUp() {
        service = mock(FileService.class);
        controller = new FileController(service);
    }
    @Test
    void uploadFile_returnsNameOfUploadedFileAndStatusOK_whenSuccessful() throws IOException {
        service = mock(FileService.class);
        controller = new FileController(service);
        var fileName = "someFileName";
        var file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(fileName);
        var expectedResult = new ResponseEntity<>(fileName, HttpStatus.OK);

        var result = controller.uploadFile(file);

        assertEquals(result, expectedResult);
    }

    @Test
    void getFileNames_returnsListOfFileNamesAndStatusOK_whenSuccessful() throws IOException {
        service = mock(FileService.class);
        controller = new FileController(service);
        var list = List.of("someFileName", "someOtherFileName");
        when(service.getFileNames()).thenReturn(list);
        var expectedResult = new ResponseEntity<>(String.join(",",list), HttpStatus.OK);

        var result = controller.getFileNames();

        assertEquals(result, expectedResult);
    }

    @Test
    void downloadFile() throws IOException {
        service = mock(FileService.class);
        controller = new FileController(service);
        var fileName = "someFileName";
        var file = mock(Resource.class);
        when(service.downloadFile(fileName)).thenReturn(file);
        var expectedResult = ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;",  "filename=\"" + fileName + "\"")
                .body(file);

        var result = controller.downloadFile(fileName);

        assertEquals(result, expectedResult);
    }
}
package com.example.demo.service;

import com.example.demo.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    private FileService service;
    private FileRepository repository;
    @BeforeEach
    public void setUp() {
        repository = mock(FileRepository.class);
        service = new FileService(repository);
    }
    @Test
    void getFileNames_returnsListOfStrings() throws IOException {
        var path = Path.of("file1");
        repository = mock(FileRepository.class);
        when(repository.getFileNames()).then(x -> Stream.of(path));
        service = new FileService(repository);
        var expectedResult = List.of("file1");

        var result = service.getFileNames();

        assertEquals(result, expectedResult);
    }

    @Test
    void uploadFile_callsRepositoryWithMultipartFile() throws IOException {
        var file = mock(MultipartFile.class);
        repository = mock(FileRepository.class);
        service = new FileService(repository);

        service.uploadFile(file);

        verify(repository).saveFile(any(MultipartFile.class));
    }

    @Test
    void downloadFile() throws MalformedURLException {
        repository = mock(FileRepository.class);
        service = new FileService(repository);
        var fileName = "someFileName";
        var resource = mock(Resource.class);
        when(repository.getFile(fileName)).thenReturn(resource);

        var result = service.downloadFile(fileName);

        assertEquals(result, resource);
    }
}
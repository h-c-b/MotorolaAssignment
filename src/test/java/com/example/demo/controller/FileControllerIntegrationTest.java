package com.example.demo.controller;

import com.example.demo.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerIntegrationTest {
    @MockBean
    FileRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void uploadFile_returnsFileNameAndStatusOK_WhenSuccessful() throws Exception {
        when(repository.saveFile(any())).thenReturn(true);

        var file = new MockMultipartFile(
                "file",
                "originalFileName",
                MediaType.TEXT_PLAIN_VALUE,
                "text".getBytes());

        mvc.perform(multipart("/api/files").file(file))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").value(file.getOriginalFilename())
                );

        verify(repository, times(1)).saveFile(any());
    }

    @Test
    public void uploadFile_returnsInternalServerError_whenIOExceptionThrown() throws Exception {
        when(repository.saveFile(any())).thenThrow(new IOException());

        var file = new MockMultipartFile(
                "file",
                "originalFileName",
                MediaType.TEXT_PLAIN_VALUE,
                "text".getBytes());

        mvc.perform(multipart("/api/files").file(file))
                .andExpectAll(
                        status().isInternalServerError()

                );

        verify(repository, times(1)).saveFile(any());
    }

    @Test
    public void uploadFile_returnsConflict_whenFileAlreadyExist() throws Exception {
        when(repository.saveFile(any())).thenThrow(new FileAlreadyExistsException(""));

        var file = new MockMultipartFile(
                "file",
                "originalFileName",
                MediaType.TEXT_PLAIN_VALUE,
                "text".getBytes());

        mvc.perform(multipart("/api/files").file(file))
                .andExpectAll(
                        status().isConflict()

                );

        verify(repository, times(1)).saveFile(any());
    }

    @Test
    public void getFileNames_returnsListOfFileNamesAndStatusOK_WhenSuccessful() throws Exception {
        var pathsStream = Stream.of(Path.of("someFileName1"), Path.of("someFileName2"));
        when(repository.getFileNames()).thenReturn(pathsStream);
        var expectedBody = String.join(",", List.of("someFileName1", "someFileName2"));

        mvc.perform(get("/api/files"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").value(expectedBody)
                );

        verify(repository, times(1)).getFileNames();
    }

    @Test
    public void getFileNames_returnsInternalServerError_whenIOExceptionThrown() throws Exception {
        when(repository.getFileNames()).thenThrow(new IOException());

        mvc.perform(get("/api/files"))
                .andExpectAll(
                        status().isInternalServerError()
                );

        verify(repository, times(1)).getFileNames();
    }

    @Test
    public void downloadFile_returnsFileAndStatusOK_WhenSuccessful() throws Exception {
        var fileName = "someFileName.txt";
        var resource =  mock(Resource.class);
        when(resource.getContentAsByteArray()).thenReturn(new byte[]{});
        when(repository.getFile(fileName)).thenReturn(resource);

        mvc.perform(get("/api/files/" + fileName))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_OCTET_STREAM),
                        content().bytes(resource.getContentAsByteArray())
                );

        verify(repository, times(1)).getFile(fileName);
    }

    @Test
    public void downloadFile_returnsBadRequest_WhenInvalidPath() throws Exception {
        var fileName = "someFileName:txt";

        mvc.perform(get("/api/files/" + fileName))
                .andExpectAll(
                        status().isBadRequest()
                );

        verify(repository, times(0)).getFile(fileName);
    }
}

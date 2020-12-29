package com.stephenmcveigh.filestorage.resource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stephenmcveigh.filestorage.dao.entity.File;
import com.stephenmcveigh.filestorage.exception.FileNotFoundException;
import com.stephenmcveigh.filestorage.exception.FileUploadException;
import com.stephenmcveigh.filestorage.service.FileStorageService;
import com.stephenmcveigh.filestorage.service.FileValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class FileResourceTest {

  @Mock
  private FileValidationService fileValidationService;
  @Mock
  private FileStorageService fileStorageService;
  @Mock
  private MockMultipartFile multipartFile;
  @Mock
  private File file;

  @InjectMocks
  private FileResource fileResource;

  @Test
  void testUploadResponseContainsId() throws IOException, FileUploadException {
    when(fileStorageService.saveFile(multipartFile)).thenReturn(1L);
    ResponseEntity<Long> response = fileResource.upload(multipartFile);
    verify(fileValidationService).validateFile(multipartFile);
    assertEquals(Long.valueOf(1L), response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testFileDownloadResponseContainsDataAndType() throws FileNotFoundException {
    when(file.getData()).thenReturn("test data".getBytes());
    when(file.getContentType()).thenReturn("image/png");
    when(fileStorageService.getFile(1L)).thenReturn(file);

    ResponseEntity<byte[]> response = fileResource.download(1L);

    assertNotNull(response.getHeaders().getContentType());
    assertEquals("image/png", response.getHeaders().getContentType().toString());
    assertArrayEquals("test data".getBytes(), response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}

package com.stephenmcveigh.filestorage.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks
  private GlobalExceptionHandler exceptionHandler;

  @Test
  void testFileUploadException() {
    ResponseEntity<HashMap<String, String>> response = exceptionHandler
        .handleUploadFail(new FileUploadException("File is too small"));

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals("File is too small", response.getBody().get("errorMessage"));
  }

  @Test
  void testMaxUploadSizeExceededException() {
    ReflectionTestUtils.setField(exceptionHandler, "maxFileSize", "1MB");

    ResponseEntity<HashMap<String, String>> response = exceptionHandler
        .handleUploadFail(new MaxUploadSizeExceededException(200000));

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals("The selected file must be smaller than 1MB",
        response.getBody().get("errorMessage"));
  }

}

package com.stephenmcveigh.filestorage.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String ERROR_MESSAGE_KEY = "errorMessage";
  private static final String EXCEPTION_MESSAGE = "The file could not be processed";
  private static final String FILE_TOO_LARGE = "The selected file must be smaller than %s";

  private static final Logger EXCEPTION_LOGGER = LoggerFactory
      .getLogger(GlobalExceptionHandler.class);

  @Value("${spring.servlet.multipart.max-file-size}")
  private String maxFileSize;

  @ExceptionHandler(value = {FileUploadException.class, MaxUploadSizeExceededException.class})
  protected ResponseEntity<HashMap<String, String>> handleUploadFail(Exception exception) {
    EXCEPTION_LOGGER.warn(EXCEPTION_MESSAGE, exception);

    String message = exception instanceof MaxUploadSizeExceededException
        ? String.format(FILE_TOO_LARGE, maxFileSize)
        : exception.getMessage();

    HashMap<String, String> body = new HashMap<>();
    body.put(ERROR_MESSAGE_KEY, message);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(body);
  }
}

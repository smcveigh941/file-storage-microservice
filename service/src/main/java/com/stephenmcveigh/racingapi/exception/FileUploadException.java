package com.stephenmcveigh.racingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileUploadException extends Exception {

  public FileUploadException(String message) {
    super(message);
  }
}

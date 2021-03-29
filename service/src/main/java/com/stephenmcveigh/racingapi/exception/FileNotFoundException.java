package com.stephenmcveigh.racingapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends Exception {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileNotFoundException.class);

  public FileNotFoundException(String message) {
    super(message);
    message = message.replaceAll("[\n\r\t]", "_");
    LOGGER.info(message);
  }
}

package com.stephenmcveigh.filestorage.service;

import com.stephenmcveigh.filestorage.exception.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class FileValidationService {

  // Validation error messages
  public static final String FILE_TYPE_IS_INCORRECT = "The uploaded file must be a JPEG or a PNG";
  public static final String FILE_TOO_SMALL = "The uploaded file must be at least 1KB in size";
  public static final String FILE_IS_EMPTY = "The uploaded file is empty";

  // Validation criteria
  public static final int EMPTY = 0;
  public static final int ONE_KB = 1024;
  protected static final String[] ALLOWED_MIME_TYPES = {"image/jpeg", "image/png"};

  public void validateFile(MultipartFile attachment) throws FileUploadException {
    long fileSize = attachment.getSize();
    if (fileSize == EMPTY) {
      throw new FileUploadException(FILE_IS_EMPTY);
    }

    if (fileSize < ONE_KB) {
      throw new FileUploadException(FILE_TOO_SMALL);
    }

    List<String> allowedExtensions = Arrays.asList(ALLOWED_MIME_TYPES);
    if (!allowedExtensions.contains(attachment.getContentType())) {
      throw new FileUploadException(FILE_TYPE_IS_INCORRECT);
    }
  }
}

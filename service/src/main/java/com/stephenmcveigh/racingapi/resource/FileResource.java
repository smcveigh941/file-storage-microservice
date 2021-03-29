package com.stephenmcveigh.racingapi.resource;

import com.stephenmcveigh.racingapi.dao.entity.File;
import com.stephenmcveigh.racingapi.exception.FileNotFoundException;
import com.stephenmcveigh.racingapi.exception.FileUploadException;
import com.stephenmcveigh.racingapi.service.FileStorageService;
import com.stephenmcveigh.racingapi.service.FileValidationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileResource {

  private final FileValidationService validationService;
  private final FileStorageService fileStorageService;

  public FileResource(FileValidationService fileValidationService,
      FileStorageService fileStorageService) {
    this.validationService = fileValidationService;
    this.fileStorageService = fileStorageService;
  }

  @PostMapping
  public ResponseEntity<Long> upload(@RequestParam MultipartFile file)
      throws FileUploadException, IOException {
    validationService.validateFile(file);

    return ResponseEntity.ok().body(fileStorageService.saveFile(file));
  }

  @GetMapping("/{id:\\d+}")
  public ResponseEntity<byte[]> download(@PathVariable long id) throws FileNotFoundException {
    File file = fileStorageService.getFile(id);
    return ResponseEntity.ok()
        .contentType(MediaType.valueOf(file.getContentType()))
        .body(file.getData());
  }
}

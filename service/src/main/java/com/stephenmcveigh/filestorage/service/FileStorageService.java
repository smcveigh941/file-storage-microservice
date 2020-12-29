package com.stephenmcveigh.filestorage.service;

import com.stephenmcveigh.filestorage.dao.entity.File;
import com.stephenmcveigh.filestorage.dao.repository.FileRepository;
import com.stephenmcveigh.filestorage.exception.FileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileStorageService {

  private static final String NOT_FOUND_ERROR = "File with ID %s does not exist";

  private final FileRepository repository;

  public FileStorageService(FileRepository repository) {
    this.repository = repository;
  }

  public File getFile(long id) throws FileNotFoundException {
    Optional<File> file = repository.findById(id);
    if (file.isEmpty()) {
      throw new FileNotFoundException(String.format(NOT_FOUND_ERROR, id));
    }
    return file.get();
  }

  public long saveFile(MultipartFile file) throws IOException {
    Optional<File> existingFile = repository.findByData(file.getBytes());
    if (existingFile.isPresent()) {
      return existingFile.get().getId();
    }

    File entity = File.builder()
        .data(file.getBytes())
        .contentType(file.getContentType())
        .build();

    repository.save(entity);

    return entity.getId();
  }
}

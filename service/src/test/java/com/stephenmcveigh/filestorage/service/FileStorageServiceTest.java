package com.stephenmcveigh.filestorage.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stephenmcveigh.filestorage.dao.entity.File;
import com.stephenmcveigh.filestorage.dao.repository.FileRepository;
import com.stephenmcveigh.filestorage.exception.FileNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FileStorageServiceTest {

  @Mock
  private FileRepository repository;
  @Mock
  private File file;
  @Mock
  private MultipartFile multipartFile;

  @InjectMocks
  private FileStorageService service;

  @Test
  void testGetFileThrowsFileNotFoundException() {
    when(repository.findById(1L)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> service.getFile(1L))
        .isInstanceOf(FileNotFoundException.class)
        .hasMessage("File with ID 1 does not exist");
  }

  @Test
  void testGetFileReturnsCorrectFile() throws FileNotFoundException {
    when(repository.findById(1L)).thenReturn(Optional.of(file));
    File fetchedFile = service.getFile(1L);
    assertSame(fetchedFile, file);
  }

  @Test
  void testSaveFileReturnsIdOfExistingFile() throws IOException {
    when(multipartFile.getBytes()).thenReturn("test data".getBytes());
    when(file.getId()).thenReturn(1L);
    when(repository.findByData(any(byte[].class))).thenReturn(Optional.of(file));
    long fileId = service.saveFile(multipartFile);
    assertEquals(1L, fileId);
  }

  @Test
  void testSaveFilePersistsNewFile() throws IOException {
    when(multipartFile.getBytes()).thenReturn("test data".getBytes());
    when(repository.findByData(any(byte[].class))).thenReturn(Optional.empty());
    service.saveFile(multipartFile);
    verify(repository).save(any(File.class));
  }
}

package com.stephenmcveigh.filestorage.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import com.stephenmcveigh.filestorage.exception.FileUploadException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class FileValidationServiceTest {

  @Mock
  private MockMultipartFile multipartFile;

  @InjectMocks
  private FileValidationService fileValidationService;

  @Test
  void testThrowsFileUploadExceptionWhenFileEmpty() {
    when(multipartFile.getSize()).thenReturn(0L);
    assertThatThrownBy(() -> fileValidationService.validateFile(multipartFile))
        .isInstanceOf(FileUploadException.class)
        .hasMessage("The uploaded file is empty");
  }

  @Test
  void testThrowsFileUploadExceptionWhenFileTooSmall() {
    when(multipartFile.getSize()).thenReturn(100L);
    assertThatThrownBy(() -> fileValidationService.validateFile(multipartFile))
        .isInstanceOf(FileUploadException.class)
        .hasMessage("The uploaded file must be at least 1KB in size");
  }

  @ParameterizedTest
  @ValueSource(strings = {"application/pdf", "text/xml", "image/svg", "image/gif", "test content type"})
  void testThrowsFileUploadExceptionWhenIllegalMimeType(String mimeType) {
    when(multipartFile.getSize()).thenReturn(1024L);
    when(multipartFile.getContentType()).thenReturn(mimeType);
    assertThatThrownBy(() -> fileValidationService.validateFile(multipartFile))
        .isInstanceOf(FileUploadException.class)
        .hasMessage("The uploaded file must be a JPEG or a PNG");
  }

  @ParameterizedTest
  @ValueSource(strings = {"image/png", "image/jpeg"})
  void testAllowsMimeType(String mimeType) {
    when(multipartFile.getSize()).thenReturn(1024L);
    when(multipartFile.getContentType()).thenReturn(mimeType);
    assertDoesNotThrow(() -> fileValidationService.validateFile(multipartFile));
  }
}

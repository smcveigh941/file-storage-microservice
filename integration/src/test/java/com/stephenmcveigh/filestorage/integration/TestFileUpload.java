package com.stephenmcveigh.filestorage.integration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import com.stephenmcveigh.filestorage.integration.helper.FileHelper;
import com.stephenmcveigh.filestorage.integration.helper.RequestHelper;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class TestFileUpload {

  private static final String POST_FILE_ENDPOINT = "/files";
  private static final int ONE_KB = 1024;
  private static final int ONE_MB = 1048576;
  private static final int EMPTY = 0;

  private File file;

  @AfterEach
  void tearDown() {
    if (file != null) {
      file.delete();
    }
  }

  @Test
  void testFileCanBeUploaded() throws IOException {
    file = FileHelper.makeFile(ONE_KB);
    RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(instanceOf(String.class));
  }

  @Test
  void testBadRequestWhenFileisEmpty() throws IOException {
    file = FileHelper.makeFile(EMPTY);
    RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body("errorMessage", equalTo("The uploaded file is empty"));
  }

  @Test
  void testBadRequestWhenFileTooSmall() throws IOException {
    file = FileHelper.makeFile(ONE_KB - 1);
    RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body("errorMessage", equalTo("The uploaded file must be at least 1KB in size"));
  }

  @Test
  void testBadRequestWhenFileTooBig() throws IOException {
    file = FileHelper.makeFile(ONE_MB + 1);
    RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body("errorMessage", equalTo("The selected file must be smaller than 1MB"));
  }

  @Test
  void testBadRequestWhenMimeTypeIsNotAllowed() throws IOException {
    file = FileHelper.makeFile(ONE_KB);
    RequestHelper.createFileUploadRequest(file, "text/xml")
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body("errorMessage", equalTo("The uploaded file must be a JPEG or a PNG"));
  }

  @Test
  void testReturnsSameIdIfSameFileUploadedTwice() throws IOException {
    file = FileHelper.makeFile(ONE_KB);
    Response response = RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT));

    String expectedId = response.body().asString();

    RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(equalTo(expectedId));
  }
}

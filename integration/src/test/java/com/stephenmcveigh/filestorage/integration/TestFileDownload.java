package com.stephenmcveigh.filestorage.integration;

import com.stephenmcveigh.filestorage.integration.helper.FileHelper;
import com.stephenmcveigh.filestorage.integration.helper.RequestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class TestFileDownload {

  private static final String POST_FILE_ENDPOINT = "/files";
  private static final String GET_FILE_ENDPOINT = "/files/%s";
  private static final int ONE_KB = 1024;

  private File file;

  @AfterEach
  void tearDown() {
    if (file != null) {
      file.delete();
    }
  }

  @Test
  void testFileCanBeDownloaded() throws IOException {
    file = FileHelper.makeFile(ONE_KB);
    String fileId = RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .getBody().asString();

    RequestHelper.createRequest()
        .get(RequestHelper.getUrl(String.format(GET_FILE_ENDPOINT, fileId)))
        .then()
        .statusCode(200)
        .contentType("image/png");
  }

  @Test
  void testThrows404WhenFileDoesNotExist() throws IOException {
    file = FileHelper.makeFile(ONE_KB);
    String fileId = RequestHelper.createFileUploadRequest(file)
        .post(RequestHelper.getUrl(POST_FILE_ENDPOINT))
        .getBody().asString();

    RequestHelper.createRequest()
        .get(RequestHelper.getUrl(String.format(GET_FILE_ENDPOINT, Integer.parseInt(fileId) + 1)))
        .then()
        .statusCode(404);
  }
}

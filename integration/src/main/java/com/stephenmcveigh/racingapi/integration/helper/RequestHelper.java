package com.stephenmcveigh.racingapi.integration.helper;


import static io.restassured.RestAssured.given;

import com.stephenmcveigh.racingapi.integration.Properties;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestHelper {

  private static final String BODY_FILE_KEY = "file";

  public static RequestSpecification createFileUploadRequest(File file) {
    return createFileUploadRequest(file, "image/png");
  }

  public static RequestSpecification createFileUploadRequest(File file, String mimeType) {
    return createMultipartRequest().multiPart(BODY_FILE_KEY, file, mimeType);
  }

  public static URL getUrl(String endpoint) throws MalformedURLException {
    return new URL(Properties.SERVICE_BASE_URL + endpoint);
  }

  public static RequestSpecification createRequest() {
    return given()
        .when();
  }

  private static RequestSpecification createMultipartRequest() {
    return given()
        .contentType(ContentType.MULTIPART_FORM_DATA.getMimeType())
        .when();
  }

  private RequestHelper() {
    // Private constructor to hide the implicit public one
  }
}

package com.stephenmcveigh.filestorage.integration;

import org.apache.commons.lang3.StringUtils;

public class Properties {

  public static final String SERVICE_BASE_URL = getPropertyOrEnv("service.base.url",
      "FILE_STORAGE_SERVICE_URL");

  private static String getPropertyOrEnv(String property, String envKey) {
    String value = System.getProperty(property);
    if (StringUtils.isEmpty(value)) {
      value = System.getenv(envKey);
    }
    return value;
  }

  private Properties() {
    // Private constructor to hide the implicit public one
  }
}

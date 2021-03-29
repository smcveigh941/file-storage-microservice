package com.stephenmcveigh.racingapi.entity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.stephenmcveigh.racingapi.dao.entity.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileTest {

  private File file;

  @BeforeEach
  void setup() {
    file = new File();
  }

  @Test
  void testId() {
    file.setId(1);
    assertEquals(1, file.getId());
  }

  @Test
  void testData() {
    file.setData("test data".getBytes());
    assertArrayEquals("test data".getBytes(), file.getData());
  }

  @Test
  void testContentType() {
    file.setContentType("test content type");
    assertEquals("test content type", file.getContentType());
  }
}

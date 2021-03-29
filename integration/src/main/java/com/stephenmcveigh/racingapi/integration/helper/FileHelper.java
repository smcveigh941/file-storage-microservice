package com.stephenmcveigh.racingapi.integration.helper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHelper {

  public static File makeFile(int sizeInBytes) throws IOException {
    File file = new File("testfile");
    if (file.exists()) {
      file.delete();
    }
    if (file.createNewFile()) {
      try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
        randomAccessFile.write(new byte[sizeInBytes]);
        return file;
      }
    } else {
      throw new IOException();
    }
  }

  private FileHelper() {
    // Private constructor to hide the implicit public one
  }
}

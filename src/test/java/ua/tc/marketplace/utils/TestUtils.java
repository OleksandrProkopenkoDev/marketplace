package ua.tc.marketplace.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {

  public static String readResourceFile(String fileName) throws IOException {
    ClassLoader classLoader = TestUtils.class.getClassLoader();
    try {
      Path path = Paths.get(classLoader.getResource(fileName).toURI());
      return new String(Files.readAllBytes(path));
    } catch (URISyntaxException e) {
      throw new IOException("Error reading resource file", e);
    }
  }
}

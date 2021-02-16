package student.adventure.gameEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.pojo.Layout;
import java.io.File;
import java.io.IOException;

/** Used to initialize the json file. */
public class Initialize {
  /**
   * File method used to put in a specific path depending on the argument.
   *
   * @param path String that is initialized with the file
   * @return a Layout object with the POJOs initialized
   * @throws IOException due to reading value through Jackson
   */
  public static Layout file(String path) throws IOException {
    if (path == null || !(path.equals("src/main/resources/prison.json"))) {
      throw new IllegalArgumentException("Cannot parse invalid JSON file.");
    }
    File file = new File(path);
    return new ObjectMapper().readValue(file, Layout.class);
  }
}

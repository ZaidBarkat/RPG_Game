package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import student.adventure.engine.GameEngine;
import student.adventure.pojo.Layout;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonTest {
  public GameEngine gameEngine = new GameEngine();

  @Test
  public void testValidJsonFile() throws IOException {
    File file = new File("src/main/resources/prison.json");
    Layout layoutTest = new ObjectMapper().readValue(file, Layout.class);

    Layout layout = gameEngine.file("src/main/resources/prison.json");
    assertEquals(layoutTest.getEndingRoom(), layout.getEndingRoom());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullJsonFile() throws IOException {
    Layout layout = gameEngine.file(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidJsonFile() throws IOException {
    Layout layout = gameEngine.file("daofnwoifnewjgneojwngojewn");
  }
}

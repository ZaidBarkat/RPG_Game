package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.Test;
import student.adventure.engine.GameEngine;
import student.adventure.pojo.Layout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonTest {
  private GameEngine gameEngine = new GameEngine();

  @Test
  public void testValidJsonFile() throws IOException {
    File file = new File("src/main/resources/prison.json");
    Layout layoutTest = new ObjectMapper().readValue(file, Layout.class);

    Layout layoutFile = Layout.file("src/main/resources/prison.json");
    assertEquals(layoutTest.getEndingRoom(), layoutFile.getEndingRoom());
  }

  @Test(expected = NullPointerException.class)
  public void testNullJsonFile() throws IOException {
    Layout layoutFile = Layout.file(null);
  }

  @Test(expected = FileNotFoundException.class)
  public void testInvalidJsonFile() throws IOException {
    Layout layoutFile = Layout.file("daofnwoifnewjgneojwngojewn");
  }

  @Test(expected = UnrecognizedPropertyException.class)
  public void testInvalidJsonFileSyntacticallyCorrect() throws IOException {
    Layout layoutFile = Layout.file("src/main/resources/jsontest.json");
  }
}

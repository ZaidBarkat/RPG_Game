package student.adventure;

import org.junit.Test;
import student.adventure.gameEngine.Input;

import static org.junit.Assert.assertTrue;

public class InputTest {

  @Test
  public void testInputExamine() {
    assertTrue(Input.isExamine("examine"));
  }

  @Test
  public void testInputGo() {
    assertTrue(Input.isGo("go"));
  }

  @Test
  public void testInputTake() {
    assertTrue(Input.isTake("take"));
  }

  @Test
  public void testInputDrop() {
    assertTrue(Input.isDrop("drop"));
  }

  @Test
  public void testInputQuit() {
    assertTrue(Input.isQuit("exit"));
  }
}

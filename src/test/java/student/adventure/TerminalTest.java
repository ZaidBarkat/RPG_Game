package student.adventure;

import org.junit.Test;
import student.adventure.engine.Terminal;

import static org.junit.Assert.assertTrue;

public class TerminalTest {
  Terminal terminal = new Terminal();

  @Test
  public void testInputExamine() {
    assertTrue(terminal.isExamine("examine"));
  }

  @Test
  public void testInputGo() {
    assertTrue(terminal.isGo("go"));
  }

  @Test
  public void testInputTake() {
    assertTrue(terminal.isTake("take"));
  }

  @Test
  public void testInputDrop() {
    assertTrue(terminal.isDrop("drop"));
  }

  @Test
  public void testInputQuit() {
    assertTrue(terminal.isQuit("exit"));
  }
}

package student.adventure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import student.adventure.engine.GameEngine;
import student.adventure.engine.Terminal;
import student.adventure.pojo.Layout;
import student.server.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameEngineTestApi {
  PrisonAdventure service = new PrisonAdventure();

  @Test(expected = NullPointerException.class)
  public void testResetPost() throws AdventureException {
    service.newGame();

    service.reset();

    service.getGame(0);
  }

  @Test
  public void testNewGamePost() throws AdventureException {
    service.newGame();

    assertEquals("Cell Block A", service.getGameEngines().get(0).getRoom().getName());
  }

  @Test
  public void testValidGetGame() throws AdventureException {
    Command command = new Command("go", "west");

    service.newGame();
    service.getGame(0);

    service.executeCommand(0, command);
    service.getGame(0);

    assertEquals("Hallway", service.getGameEngines().get(0).getRoom().getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetGameInvalidId() throws AdventureException {
    service.newGame();
    service.getGame(-1);
  }

  @Test(expected = NullPointerException.class)
  public void testInvalidGetGameIdNotCreated() throws AdventureException {
    service.newGame();
    service.getGame(4);
  }

  @Test
  public void testValidExecuteCommandDifferentInstances() throws AdventureException {
    Command command = new Command("go", "west");

    service.newGame();
    service.newGame();

    service.getGame(0);
    service.getGame(1);

    service.executeCommand(0, command);
    service.executeCommand(1, command);
    service.getGame(0);
    service.getGame(1);

    assertEquals(
        service.getGameEngines().get(0).getRoom().getName(),
        service.getGameEngines().get(1).getRoom().getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidExecuteCommandInvalidId() {
    Command command = new Command("go", "west");

    service.executeCommand(-1, command);
  }

  @Test(expected = NullPointerException.class)
  public void testInvalidExecuteCommandNoIdInstance() {
    Command command = new Command("go", "west");

    service.executeCommand(4, command);
  }

  @Test
  public void testValidDestroyGame() throws AdventureException {
    service.newGame();

    service.getGame(0);

    service.destroyGame(0);

    assertTrue(service.getGameEngines().isEmpty());
  }

  @Test
  public void testValidDestroyGameNoInstanceId() {
    assertFalse(service.destroyGame(4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDestroyGameInvalidId() {
    service.destroyGame(-1);
  }
}

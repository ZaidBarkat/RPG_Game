package student.adventure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import student.adventure.engine.GameEngine;
import student.adventure.engine.Terminal;
import student.adventure.pojo.Layout;

import java.io.IOException;

public class GameEngineTest {
  Layout layout;
  GameEngine gameEngine = new GameEngine();
  Terminal terminal = new Terminal();

  @Before
  public void setUp() throws IOException {
    layout = gameEngine.file("src/main/resources/prison.json");
  }

  @Test
  public void testStartState() {
    gameEngine.startState();

    Assert.assertEquals(layout.getStartingRoom(), gameEngine.getRoom().getName());
  }

  @Test
  public void testValidGoState() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.goState(terminal.handleInput("go east"));

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidGoStateWithSpace() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.goState(terminal.handleInput("go                east"));

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidGoStateWithTrailingSpace() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.goState(terminal.handleInput("go       east            "));

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidGoStateCapitalLetters() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.goState(terminal.handleInput("go EAST"));

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidGoStateCapitalAndLowerCase() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.goState(terminal.handleInput("go eAsT"));

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testInvalidGoState() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.goState(terminal.handleInput("go      HerE"));

    Assert.assertEquals("Bathroom", gameEngine.getRoom().getName());
  }

  @Test
  public void testExamineState() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.examineState();

    Assert.assertEquals("Bathroom", gameEngine.getRoom().getName());
  }

  @Test
  public void testInvalidTakeState() {
    gameEngine.getInventory().clear();

    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take    SomeThing"));

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testValidTakeState() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take id"));

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidTakeStateLeadingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take       id"));

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidTakeStateTrailingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take   id        "));

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidTakeStateCapitalAndLowerCases() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take   iD        "));

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testInvalidDropState() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take   iD        "));
    gameEngine.dropState(terminal.handleInput("drop    something        "));

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidDropState() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take   iD   "));
    gameEngine.dropState(terminal.handleInput("drop   iD "));

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testValidDropStateTrailingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take   iD        "));
    gameEngine.dropState(terminal.handleInput("drop   iD        "));

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testValidDropStateLeadingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    gameEngine.takeState(terminal.handleInput("take   iD        "));
    gameEngine.dropState(terminal.handleInput("drop              iD        "));

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testWinCondition() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Vent"));

    gameEngine.goState(new String[] {"go", "south"});

    Assert.assertTrue(gameEngine.isWinCondition());
  }
}

package student.adventure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import student.adventure.engine.GameEngine;
import student.adventure.engine.Terminal;
import student.adventure.pojo.Layout;

import java.io.IOException;
import java.util.ArrayList;

public class GameEngineTest {
  Layout layout;
  GameEngine gameEngine;
  Terminal terminal = new Terminal();
  String[] input;

  @Before
  public void setUp() {
    gameEngine = new GameEngine();
    layout = gameEngine.getLayout();
  }

  @Test
  public void testHandleStartCommand() {
    gameEngine.handleStartCommand();

    Assert.assertEquals(layout.getStartingRoom(), gameEngine.getRoom().getName());
  }

  @Test
  public void testValidHandleGoCommand() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("go east"), gameEngine);

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidHandleGoCommandWithSpace() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Cafeteria"));

    terminal.runGameFromTerminal(terminal.handleInput("go                WeSt"), gameEngine);

    Assert.assertEquals("Bathroom", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidHandleGoCommandWithTrailingSpace() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Hallway"));

    terminal.runGameFromTerminal(
        terminal.handleInput("go       SouthEast            "), gameEngine);

    Assert.assertEquals("Cell Block B", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidHandleGoCommandCapitalLetters() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("go EAST"), gameEngine);

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testValidHandleGoCommandCapitalAndLowerCase() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("Go eAsT"), gameEngine);

    Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
  }

  @Test
  public void testInvalidHandleGoCommand() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("gO      HerE"), gameEngine);

    Assert.assertEquals("Bathroom", gameEngine.getRoom().getName());
  }

  @Test
  public void testHandleExamineCommand() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Cell Block A"));

    terminal.runGameFromTerminal(terminal.handleInput("exAmIne"), gameEngine);

    Assert.assertEquals("Cell Block A", gameEngine.getRoom().getName());
  }

  @Test
  public void testHandleHistoryCommand() {
    ArrayList<String> playerPathTest = new ArrayList<>();

    playerPathTest.add("Cell Block A");
    playerPathTest.add("Hallway");

    gameEngine.handleStartCommand();

    terminal.runGameFromTerminal(terminal.handleInput("Go West"), gameEngine);
    terminal.runGameFromTerminal(terminal.handleInput("HIStORY"), gameEngine);

    Assert.assertEquals(playerPathTest, gameEngine.getPlayerPath());
  }

  @Test
  public void testInvalidHandleTakeCommand() {
    gameEngine.getInventory().clear();

    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Lounge"));

    terminal.runGameFromTerminal(terminal.handleInput("take      someThIng"), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testValidHandleTakeCommand() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Lounge"));

    terminal.runGameFromTerminal(terminal.handleInput("take cup"), gameEngine);

    Assert.assertTrue(
        gameEngine.getInventory().contains("cup")
            && !gameEngine.getRoom().getItems().contains("cup"));
  }

  @Test
  public void testValidHandleTakeCommandLeadingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("take       id"), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidHandleTakeCommandTrailingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("Take   id        "), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidHandleTakeCommandCapitalAndLowerCases() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("tAkE   iD        "), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testInvalidHandleDropCommand() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("take   iD        "), gameEngine);
    terminal.runGameFromTerminal(terminal.handleInput("drop    something        "), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().contains("id"));
  }

  @Test
  public void testValidHandleDropCommand() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Lounge"));

    terminal.runGameFromTerminal(terminal.handleInput("tAkE   CuP   "), gameEngine);
    terminal.runGameFromTerminal(terminal.handleInput("dRoP   Cup "), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testValidHandleDropCommandTrailingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("take   iD        "), gameEngine);
    terminal.runGameFromTerminal(terminal.handleInput("drop   iD        "), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testValidHandleDropCommandLeadingSpaces() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

    terminal.runGameFromTerminal(terminal.handleInput("take   iD        "), gameEngine);
    terminal.runGameFromTerminal(terminal.handleInput("drop              iD        "), gameEngine);

    Assert.assertTrue(gameEngine.getInventory().isEmpty());
  }

  @Test
  public void testWinCondition() {
    gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Vent"));

    terminal.runGameFromTerminal(terminal.handleInput("go    SoUth"), gameEngine);

    Assert.assertTrue(gameEngine.isWinCondition());
  }
}

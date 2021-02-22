package student.adventure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import student.adventure.engine.GameEngine;
import student.adventure.engine.Terminal;
import student.adventure.pojo.Layout;

import java.io.IOException;
import java.util.ArrayList;

public class GameEngineTestApi {
    Layout layout;
    GameEngine gameEngine;
    Terminal terminal = new Terminal();

    @Before
    public void setUp() {
        gameEngine = new GameEngine();
        layout = gameEngine.getLayout();
    }

    @Test
    public void testResetCommand() {
        gameEngine.handleStartCommand();

        Assert.assertEquals(layout.getStartingRoom(), gameEngine.getRoom().getName());
    }

    @Test
    public void testValidHandleGoCommand() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("go east"), gameEngine);

        Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
    }

    @Test
    public void testValidHandleGoCommandWithSpace() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Cafeteria"));

        gameEngine.runGame(terminal.handleInput("go                WeSt"), gameEngine);

        Assert.assertEquals("Bathroom", gameEngine.getRoom().getName());
    }

    @Test
    public void testValidHandleGoCommandWithTrailingSpace() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Hallway"));

        gameEngine.runGame(
                terminal.handleInput("go       SouthEast            "), gameEngine);

        Assert.assertEquals("Cell Block B", gameEngine.getRoom().getName());
    }

    @Test
    public void testValidHandleGoCommandCapitalLetters() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("go EAST"), gameEngine);

        Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
    }

    @Test
    public void testValidHandleGoCommandCapitalAndLowerCase() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("Go eAsT"), gameEngine);

        Assert.assertEquals("Cafeteria", gameEngine.getRoom().getName());
    }

    @Test
    public void testInvalidHandleGoCommand() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("gO      HerE"), gameEngine);

        Assert.assertEquals("Bathroom", gameEngine.getRoom().getName());
    }

    @Test
    public void testHandleExamineCommand() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Cell Block A"));

        gameEngine.runGame(terminal.handleInput("exAmIne"), gameEngine);

        Assert.assertEquals("Cell Block A", gameEngine.getRoom().getName());
    }

    @Test
    public void testHandleHistoryCommand() {
        ArrayList<String> playerPathTest = new ArrayList<>();

        playerPathTest.add("Cell Block A");
        playerPathTest.add("Hallway");

        gameEngine.handleStartCommand();

        gameEngine.runGame(terminal.handleInput("Go West"), gameEngine);
        gameEngine.runGame(terminal.handleInput("HIStORY"), gameEngine);

        Assert.assertEquals(playerPathTest, gameEngine.getPlayerPath());
    }

    @Test
    public void testInvalidHandleTakeCommand() {
        gameEngine.getInventory().clear();

        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Lounge"));

        gameEngine.runGame(terminal.handleInput("take      someThIng"), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().isEmpty());
    }

    @Test
    public void testValidHandleTakeCommand() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Lounge"));

        gameEngine.runGame(terminal.handleInput("take cup"), gameEngine);

        Assert.assertTrue(
                gameEngine.getInventory().contains("cup")
                        && !gameEngine.getRoom().getItems().contains("cup"));
    }

    @Test
    public void testValidHandleTakeCommandLeadingSpaces() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("take       id"), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().contains("id"));
    }

    @Test
    public void testValidHandleTakeCommandTrailingSpaces() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("Take   id        "), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().contains("id"));
    }

    @Test
    public void testValidHandleTakeCommandCapitalAndLowerCases() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("tAkE   iD        "), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().contains("id"));
    }

    @Test
    public void testInvalidHandleDropCommand() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("take   iD        "), gameEngine);
        gameEngine.runGame(terminal.handleInput("drop    something        "), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().contains("id"));
    }

    @Test
    public void testValidHandleDropCommand() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Lounge"));

        gameEngine.runGame(terminal.handleInput("tAkE   CuP   "), gameEngine);
        gameEngine.runGame(terminal.handleInput("dRoP   Cup "), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().isEmpty());
    }

    @Test
    public void testValidHandleDropCommandTrailingSpaces() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("take   iD        "), gameEngine);
        gameEngine.runGame(terminal.handleInput("drop   iD        "), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().isEmpty());
    }

    @Test
    public void testValidHandleDropCommandLeadingSpaces() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Bathroom"));

        gameEngine.runGame(terminal.handleInput("take   iD        "), gameEngine);
        gameEngine.runGame(terminal.handleInput("drop              iD        "), gameEngine);

        Assert.assertTrue(gameEngine.getInventory().isEmpty());
    }

    @Test
    public void testWinCondition() {
        gameEngine.setRoom(layout.findByRoomName(layout.getRooms(), "Vent"));

        gameEngine.runGame(terminal.handleInput("go    SoUth"), gameEngine);

        Assert.assertTrue(gameEngine.isWinCondition());
    }
}

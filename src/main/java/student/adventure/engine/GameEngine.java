package student.adventure.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.pojo.Direction;
import student.adventure.pojo.Layout;
import student.adventure.pojo.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Game engine of adventure, changes variable values through methods. */
public class GameEngine {
  private Room room;
  private boolean isGameDone = false;
  private List<String> directionNames = new ArrayList<>();
  private List<String> inventory = new ArrayList<>();
  private List<String> playerPath = new ArrayList<>();
  private Layout layout;
  private int instanceId;

  /**
   * Create gameEngine with id for API, sets json file
   *
   * @param id to keep track of instances of GameEngine
   */
  public GameEngine(int id) {
    instanceId = id;
    try {
      layout = Layout.file("src/main/resources/prison.json");
    } catch (Exception e) {
      e.printStackTrace();
      layout = null;
    }
  }

  /**
   * GameEngine constructor, sets json file
   */
  public GameEngine() {
    try {
      layout = Layout.file("src/main/resources/prison.json");
    } catch (Exception e) {
      e.printStackTrace();
      layout = null;
    }
  }

  /**
   * static stream used to find GameEngine object by id value
   *
   * @param gameEngine list of GameEngine instances
   * @param id to find GameEngine object
   * @return GameEngine or null if none found
   */
  public static GameEngine findByGameEngineId(Collection<GameEngine> gameEngine, int id) {
    return gameEngine.stream()
            .filter(game -> id == game.getInstanceId())
            .findFirst()
            .orElse(null);
  }

  /**
   * Start command, sets room based on starting room and adds direction names (North, South, etc..)
   * to list, also adding room name to player path
   *
   * @return A string of what is to be displayed
   */
  public String handleStartCommand() {
    room = layout.findByRoomName(layout.getRooms(), layout.getStartingRoom());

    addDirectionNames();

    playerPath.add(layout.getStartingRoom());

    return generatePrint();
  }

  /**
   * Go command, sets room based on direction name, while creating new directions and adding the
   * room to the player path
   *
   * @param input Of direction name such as (North, South, East, etc..)
   * @return a String of what is to be displayed
   */
  private String handleGoCommand(String input) {
    Direction direction;

    if (room.findByDirectionName(room.getDirections(), input) == null) {
      return "You can't go " + input;
    } else {
      direction = room.findByDirectionName(room.getDirections(), input);
    }

    room = layout.findByRoomName(layout.getRooms(), direction.getRoom());

    directionNames.clear();

    playerPath.add(room.getName());

    addDirectionNames();

    if (isWinCondition()) {
      return room.getDescription();
    }

    return generatePrint();
  }

  /** Examine command */
  private String handleExamineCommand() {
    return generatePrint();
  }

  /**
   * If items list contains the item, then move it to an inventory list and take it out of the item
   * list.
   *
   * @param input used to make sure the item is in the items list
   */
  private String handleTakeCommand(String input) {
    if (room.getItems().contains(input)) {
      inventory.add(input);
      room.getItems().remove(input);
    } else {
      return "There is no item " + input + " in the room";
    }

    return "took";
  }

  /**
   * If inventory list contains the item, then move it to an item list and take it out of the
   * inventory list.
   *
   * @param input used to make sure the item is in the items list
   */
  private String handleDropCommand(String input) {
    if (inventory.contains(input)) {
      room.getItems().add(input);
      inventory.remove(input);
    } else {
      return "You don't have " + input + "!";
    }

    return "dropped";
  }

  public String handleHistoryCommand() {
    return "Path Taken: " + playerPath;
  }

  /**
   * Checks to see if the room objects name is the ending room.
   *
   * @return true if you are in the ending room
   */
  public boolean isWinCondition() {
    return room.getName().equals(layout.getEndingRoom());
  }

  /** Illustrating the description, directionNames, and Items in the current room. */
  private String generatePrint() {
    return room.getDescription()
        + "\n"
        + "From here, you can go: "
        + directionNames
        + "\n"
        + "Items visible: "
        + room.getItems();
  }

  /** Used to add directionNames into a list from the room objects. */
  private void addDirectionNames() {
    for (Direction direction : room.getDirections()) {
      directionNames.add(direction.getDirectionName());
    }
  }

  public String runGame(String[] inputArray, GameEngine gameEngine) {

    if (inputArray[0].contains("go")) {
      return gameEngine.handleGoCommand(inputArray[1]);

    } else if (inputArray[0].contains("examine")) {
      return gameEngine.handleExamineCommand();

    } else if (inputArray[0].contains("quit") || inputArray[0].contains("exit")) {
      gameEngine.setGameDone(true);
      return "Thanks For Playing";

    } else if (inputArray[0].contains("take")) {
      return gameEngine.handleTakeCommand(inputArray[1]);

    } else if (inputArray[0].contains("drop")) {
      return gameEngine.handleDropCommand(inputArray[1]);

    } else if (inputArray[0].contains("history")) {
      return gameEngine.handleHistoryCommand();
    }
    return "";
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public boolean isGameDone() {
    return isGameDone;
  }

  public void setGameDone(boolean gameDone) {
    isGameDone = gameDone;
  }

  public List<String> getInventory() {
    return inventory;
  }

  public int getInstanceId() {
    return instanceId;
  }

  public List<String> getDirectionNames() {
    return directionNames;
  }

  public Layout getLayout() {
    return layout;
  }

  public List<String> getPlayerPath() {
    return playerPath;
  }
}

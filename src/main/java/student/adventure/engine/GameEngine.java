package student.adventure.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.pojo.Direction;
import student.adventure.pojo.Layout;
import student.adventure.pojo.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

  public GameEngine(int id) {
     instanceId = id;
     try {
        layout = Layout.file("src/main/resources/prison.json");
     } catch(Exception e) {
       e.printStackTrace();
       layout = null;
     }
  }

  public GameEngine() {
    try {
      layout = Layout.file("src/main/resources/prison.json");
    } catch(Exception e) {
      e.printStackTrace();
      layout = null;
    }
  }

  /**
   * Used to start the adventure game, initializes the variables as the starting room in the layout.
   */
  public String handleStartCommand() {
    room = layout.findByRoomName(layout.getRooms(), layout.getStartingRoom());

    addDirectionNames();

    playerPath.add(layout.getStartingRoom());

    return generatePrint();
  }

  /**
   * Ran everytime an input starts with go, changed the variables depending on which way the
   * direction goes.
   *
   * @param input user input array used to check if the direction is valid
   */
  public String handleGoCommand(String input) {
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

  /** If examine is called, restates the variables. */
  public String handleExamineCommand() {
    return generatePrint();
  }

  /**
   * If items list contains the item, then move it to an inventory list and take it out of the item
   * list.
   *
   * @param input used to make sure the item is in the items list
   */
  public String handleTakeCommand(String input) {
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
  public String handleDropCommand(String input) {
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
    return
        room.getDescription()
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

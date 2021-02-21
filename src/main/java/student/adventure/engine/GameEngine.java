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

   {
    try {
      layout = file("src/main/resources/prison.json");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public GameEngine(int id) {
     instanceId = id;
  }

  public GameEngine() {}

  /**
   * Used to start the adventure game, initializes the variables as the starting room in the layout.
   */
  public String startState() {
    room = layout.findByRoomName(layout.getRooms(), layout.getStartingRoom());

    addDirectionNames();

    playerPath.add(layout.getStartingRoom());

    return textOutput();
  }

  /**
   * Ran everytime an input starts with go, changed the variables depending on which way the
   * direction goes.
   *
   * @param input user input array used to check if the direction is valid
   */
  public String goState(String input) {
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

    return textOutput();
  }

  /** If examine is called, restates the variables. */
  public String examineState() {
    return textOutput();
  }

  /**
   * If items list contains the item, then move it to an inventory list and take it out of the item
   * list.
   *
   * @param input used to make sure the item is in the items list
   */
  public String takeState(String input) {
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
  public String dropState(String input) {
    if (inventory.contains(input)) {
      room.getItems().add(input);
      inventory.remove(input);
    } else {
      return "You don't have " + input + "!";
    }

    return "dropped";
  }

  public String historyState() {
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
  private String textOutput() {
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

  public Layout file(String path) throws IOException {
    if (path == null || !(path.equals("src/main/resources/prison.json"))) {
      throw new IllegalArgumentException("Cannot parse invalid JSON file.");
    }
    File file = new File(path);
    return new ObjectMapper().readValue(file, Layout.class);
  }

  public void runGameFromTerminal(String[] inputArray) {
    Terminal terminal = new Terminal();

    if (terminal.isGo(inputArray[0])) {
      terminal.terminalOutput(goState(inputArray[1]));

    } else if (terminal.isExamine(inputArray[0])) {
      terminal.terminalOutput(examineState());

    } else if (terminal.isQuit(inputArray[0])) {
      isGameDone = true;

    } else if (terminal.isTake(inputArray[0])) {
      terminal.terminalOutput(takeState(inputArray[1]));

    } else if (terminal.isDrop(inputArray[0])) {
      terminal.terminalOutput(dropState(inputArray[1]));

    } else if (terminal.isHistory(inputArray[0])) {
      terminal.terminalOutput(historyState());
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

  public List<String> getInventory() {
    return inventory;
  }

  public int getInstanceId() {
    return instanceId;
  }

  public List<String> getDirectionNames() {
    return directionNames;
  }
}

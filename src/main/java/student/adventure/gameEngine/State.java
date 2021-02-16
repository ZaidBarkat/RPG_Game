package student.adventure.gameEngine;

import student.adventure.pojo.Direction;
import student.adventure.pojo.Layout;
import student.adventure.pojo.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Game engine of adventure, changes variable values through methods. */
public class State {
  public static Room room;
  public static String description = null;
  public static List<String> items = new ArrayList<>();
  public static List<String> directionNames = new ArrayList<>();
  public static List<String> inventory = new ArrayList<>();
  private static Layout layout;

  static {
    try {
      layout = Initialize.file("src/main/resources/prison.json");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Used to start the adventure game, initializes the variables as the starting room in the layout.
   */
  public static void startState() {
    Room startingRoom = findByRoomName(layout.getRooms(), layout.getStartingRoom());
    room = startingRoom;
    description = startingRoom.getDescription();
    items = startingRoom.getItems();

    addDirectionNames();

    textOutput();
  }

  /**
   * Ran everytime an input starts with go, changed the variables depending on which way the
   * direction goes.
   *
   * @param input user input array used to check if the direction is valid
   */
  public static void goState(String[] input) {
    Direction direction;

    if (isNull(findByDirectionName(room.getDirections(), input))) {
      System.out.println("You can't go " + input[1]);
      return;
    } else {
      direction = findByDirectionName(room.getDirections(), input);
    }

    Room currentRoom = findByRoomName(layout.getRooms(), direction.getRoom());
    room = currentRoom;
    description = currentRoom.getDescription();

    if (isNull(currentRoom.getItems())) {
      items.clear();
    } else {
      items = currentRoom.getItems();
    }

    directionNames.clear();

    addDirectionNames();

    if (winCondition()) {
      System.out.println(description);
      return;
    }

    textOutput();
  }

  /** If examine is called, restates the variables. */
  public static void examineState() {
    textOutput();
  }

  /**
   * If items list contains the item, then move it to an inventory list and take it out of the item
   * list.
   *
   * @param input used to make sure the item is in the items list
   */
  public static void takeState(String[] input) {
    if (items.contains(input[1])) {
      inventory.add(input[1]);
      items.remove(input[1]);
    } else {
      System.out.println("There is no item " + input[1] + " in the room");
    }
  }

  /**
   * If inventory list contains the item, then move it to an item list and take it out of the
   * inventory list.
   *
   * @param input used to make sure the item is in the items list
   */
  public static void dropState(String[] input) {
    if (inventory.contains(input[1])) {
      items.add(input[1]);
      inventory.remove(input[1]);
    } else {
      System.out.println("You don't have " + input[1] + "!");
    }
  }

  /**
   * Checks to see if the room objects name is the ending room.
   *
   * @return true if you are in the ending room
   */
  public static boolean winCondition() {
    return room.getName().equals(layout.getEndingRoom());
  }

  /**
   * Stream used to find a room just by the room name.
   *
   * @param listRoom The room list
   * @param roomName the string of the room name
   * @return the room object or null if not found
   */
  public static Room findByRoomName(Collection<Room> listRoom, String roomName) {
    return listRoom.stream()
        .filter(room -> roomName.equals(room.getName()))
        .findFirst()
        .orElse(null);
  }

  /**
   * Stream used to find direction object through direction name.
   *
   * @param listDirection list of directions
   * @param directionName name of direction
   * @return direction object
   */
  public static Direction findByDirectionName(
      Collection<Direction> listDirection, String[] directionName) {
    return listDirection.stream()
        .filter(direction -> directionName[1].equals(direction.getDirectionName().toLowerCase()))
        .findFirst()
        .orElse(null);
  }

  /** Illustrating the description, directionNames, and Items in the current room. */
  private static void textOutput() {
    System.out.println(
        description
            + "\n"
            + "From here, you can go: "
            + directionNames
            + "\n"
            + "Items visible: "
            + items);
  }

  /** Used to add directionNames into a list from the room objects. */
  private static void addDirectionNames() {
    for (Direction direction : room.getDirections()) {
      directionNames.add(direction.getDirectionName());
    }
  }

  /**
   * Checking if an object is null.
   *
   * @param object that is being checked
   * @return true if object is null
   */
  private static boolean isNull(Object object) {
    return object == null;
  }
}

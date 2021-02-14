package student.adventure.gameEngine;

import student.adventure.pojo.Direction;
import student.adventure.pojo.Layout;
import student.adventure.pojo.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class State {
  private static Layout layout;

  static {
    try {
      layout = Initialize.file("src/main/resources/prison.json");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Room room;
  private static String description = "";
  private static List<String> items = new ArrayList<>();
  private static List<String> directionNames = new ArrayList<>();
  private static List<String> inventory = new ArrayList<>();

  public static void startState() {
    Room startingRoom = findByRoomName(layout.getRooms(), layout.getStartingRoom());
    room = startingRoom;
    description = startingRoom.getDescription();
    items = startingRoom.getItems();

    addDirectionNames(startingRoom);

    textOutput(description, directionNames, items);
  }

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

    addDirectionNames(currentRoom);

    if (currentRoom.getName().equals(layout.getEndingRoom())) {
      System.out.println(description);
      return;
    }

    textOutput(description, directionNames, items);
  }

  public static void examineState() {
    textOutput(description, directionNames, items);
  }

  public static void takeState(String[] input) {
    if (items.contains(input[1])) {
      inventory.add(input[1]);
      items.remove(input[1]);
    } else {
      System.out.println("There is no item " + input[1] + " in the room");
    }
  }

  public static void dropState(String[] input) {
    if (inventory.contains(input[1])) {
      items.add(input[1]);
      inventory.remove(input[1]);
    } else {
      System.out.println("You don't have " + input[1] + "!");
    }
  }

  public static boolean winCondition() {
    return room.getName().equals(layout.getEndingRoom());
  }

  private static void textOutput(
      String description, List<String> directionNames, List<String> items) {
    System.out.println(
        description
            + "\n"
            + "From here, you can go: "
            + directionNames
            + "\n"
            + "Items visible: "
            + items);
  }

  private static void addDirectionNames(Room room) {
    for (Direction direction : room.getDirections()) {
      directionNames.add(direction.getDirectionName());
    }
  }

  private static Room findByRoomName(Collection<Room> listRoom, String name) {
    return listRoom.stream().filter(room -> name.equals(room.getName())).findFirst().orElse(null);
  }

  private static Direction findByDirectionName(
      Collection<Direction> listDirection, String[] directionName) {
    return listDirection.stream()
        .filter(direction -> directionName[1].equals(direction.getDirectionName().toLowerCase()))
        .findFirst()
        .orElse(null);
  }

  private static boolean isNull(Object object) {
    return object == null;
  }
}

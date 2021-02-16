package student.adventure.pojo;

import java.util.List;

/** Includes starting and ending room, as well as the list of rooms created in the json. */
public class Layout {
  private String startingRoom;
  private String endingRoom;
  private List<Room> rooms;

  public String getStartingRoom() {
    return startingRoom;
  }

  public String getEndingRoom() {
    return endingRoom;
  }

  public List<Room> getRooms() {
    return rooms;
  }
}

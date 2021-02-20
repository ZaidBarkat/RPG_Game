package student.adventure.pojo;

import java.util.Collection;
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

  /**
   * Stream used to find a room just by the room name.
   *
   * @param listRoom The room list
   * @param roomName the string of the room name
   * @return the room object or null if not found
   */
  public Room findByRoomName(Collection<Room> listRoom, String roomName) {
    return listRoom.stream()
            .filter(room -> roomName.equals(room.getName()))
            .findFirst()
            .orElse(null);
  }
}

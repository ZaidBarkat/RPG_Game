package student.adventure.pojo;

import java.util.List;

/** Room POJO used to give every room a description, direction, item, and name. */
public class Room {
  private String name;
  private String description;
  private List<String> items;
  private List<Direction> directions;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getItems() {
    return items;
  }

  public List<Direction> getDirections() {
    return directions;
  }
}

package student.adventure.pojo;

import java.util.Collection;
import java.util.List;

/** Room POJO used to give every room a description, direction, item, and name. */
public class Room {
  private String name;
  private String description;
  private List<String> items;
  private List<Direction> directions;
  private String image;

  public String getImage() {
    return image;
  }

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

  /**
   * Stream used to find direction object through direction name.
   *
   * @param listDirection list of directions
   * @param directionName name of direction
   * @return direction object
   */
  public Direction findByDirectionName(
          Collection<Direction> listDirection, String directionName) {
    return listDirection.stream()
            .filter(direction -> directionName.equals(direction.getDirectionName().toLowerCase()))
            .findFirst()
            .orElse(null);
  }
}

package student.adventure.pojo;

import java.util.List;

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

package student.adventure.pojo;

import java.util.List;

public class room {
    private String name;
    private String Description;
    private List<String> items;
    private List<Direction> directions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

    public List<String> getItems() {
        return items;
    }

    public List<Direction> getDirections() {
        return directions;
    }
}

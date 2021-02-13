package student.adventure.pojo;

import java.util.List;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private List<room> rooms;

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public List<room> getRooms() {
        return rooms;
    }
}

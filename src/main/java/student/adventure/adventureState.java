package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.pojo.Direction;
import student.adventure.pojo.Layout;
import student.adventure.pojo.Room;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class adventureState {
    public static String startState() throws IOException {
        File file = new File("src/main/resources/prison.json");
        Layout startRoom = new ObjectMapper().readValue(file, Layout.class);
        String description = "";
        List<String> items = new ArrayList<>();
        List<String> directions = new ArrayList<>();

        for (Room room : startRoom.getRooms()) {
            if (room.getName().equals(startRoom.getStartingRoom())) {
                description = room.getDescription();
                items = room.getItems();
                for (Direction direction : room.getDirections()) {
                    directions.add(direction.getDirectionName());
                }
            }
        }

        return description + "\n" + "From here, you can go: " + directions + "\n" + "Items visible: " + items;
    }


}

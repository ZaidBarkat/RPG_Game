package student.adventure.gameEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.pojo.Layout;
import java.io.File;
import java.io.IOException;

public class Initialize {
    public static Layout file(String path) throws IOException {
        if (path == null || !(path.equals("src/main/resources/prison.json"))) {
            throw new IllegalArgumentException("Cannot parse invalid JSON file.");
        }
        File file = new File(path);
        return new ObjectMapper().readValue(file, Layout.class);
    }
}

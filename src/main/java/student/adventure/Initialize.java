package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.pojo.Layout;
import java.io.File;
import java.io.IOException;

public class Initialize {
    public static Layout file(String path) throws IOException {
        File file = new File(path);
        return new ObjectMapper().readValue(file, Layout.class);
    }
}

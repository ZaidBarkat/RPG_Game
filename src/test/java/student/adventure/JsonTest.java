package student.adventure;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import student.adventure.gameEngine.Initialize;
import student.adventure.pojo.Layout;

import java.io.IOException;


public class JsonTest {
    @Before
    public void setUp() throws IOException {
        Layout layout = Initialize.file("src/main/resources/prison.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullJsonFile() throws IOException {
        Layout layout = Initialize.file(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidJsonFile() throws IOException {
        Layout layout = Initialize.file("daofnwoifnewjgneojwngojewn");
    }
}
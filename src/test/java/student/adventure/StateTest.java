package student.adventure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import student.adventure.gameEngine.Initialize;
import student.adventure.gameEngine.State;
import student.adventure.pojo.Layout;
import student.adventure.pojo.Room;

import java.io.IOException;

public class StateTest {
  Layout layout;

  @Before
  public void setUp() throws IOException {
    layout = Initialize.file("src/main/resources/prison.json");
  }

  @Test
  public void testStartState() {
    State.startState();

    Assert.assertEquals(layout.getStartingRoom(), State.room.getName());
  }

    @Test
    public void testValidGoState() {
        State.room = State.findByRoomName(layout.getRooms(), "Bathroom");

        State.goState(new String[] {"go", "east"});

        Assert.assertEquals("Cafeteria", State.room.getName());
    }

  @Test
  public void testInvalidGoState() {
    State.room = State.findByRoomName(layout.getRooms(), "Bathroom");

    State.goState(new String[]{"go", "here"});

    Assert.assertNull(State.description);
  }

  @Test
  public void testExamineState() {
    State.room = State.findByRoomName(layout.getRooms(), "Bathroom");

    State.examineState();

    Assert.assertEquals("Bathroom", State.room.getName());
  }

  @Test
  public void testInvalidTakeState() {
    State.inventory.clear();

    State.room = State.findByRoomName(layout.getRooms(), "Hallway");

    State.goState(new String[] {"go", "west"});
    State.takeState(new String[] {"take", "something"});

    System.out.println(State.inventory);
    Assert.assertTrue(State.inventory.isEmpty());
  }

  @Test
  public void testValidTakeState() {
    State.room = State.findByRoomName(layout.getRooms(), "Cafeteria");

    State.goState(new String[] {"go", "northeast"});
    State.takeState(new String[] {"take", "toothbrush"});

    Assert.assertTrue(State.inventory.contains("toothbrush"));
  }

  @Test
  public void testInvalidDropState() {
    State.room = State.findByRoomName(layout.getRooms(), "Cafeteria");

    State.goState(new String[] {"go", "northeast"});
    State.dropState(new String[] {"drop", "toothbrush"});

    Assert.assertTrue(State.inventory.isEmpty());
  }

  @Test
  public void testValidDropState() {
    State.room = State.findByRoomName(layout.getRooms(), "Cafeteria");

    State.goState(new String[] {"go", "northeast"});
    State.takeState(new String[] {"take", "screwdriver"});
    State.dropState(new String[] {"drop", "screwdriver"});

    Assert.assertTrue(State.inventory.isEmpty());
  }

  @Test
  public void testWinCondition() {
    State.room = State.findByRoomName(layout.getRooms(), "Vent");

    State.goState(new String[] {"go", "south"});

    Assert.assertTrue(State.winCondition());
  }
}

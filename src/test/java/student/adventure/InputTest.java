package student.adventure;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import student.adventure.gameEngine.Initialize;
import student.adventure.gameEngine.Input;
import student.adventure.pojo.Layout;

import java.io.IOException;

import static org.junit.Assert.*;


public class InputTest {

    @Test
    public void testInputExamine() {
        assertTrue(Input.isExamine("examine"));
    }

    @Test
    public void testInputGo() {
        assertTrue(Input.isGo("go"));
    }

    @Test
    public void testInputTake() {
        assertTrue(Input.isTake("take"));
    }

    @Test
    public void testInputDrop() {
        assertTrue(Input.isDrop("drop"));
    }

    @Test
    public void testInputQuit() {
        assertTrue(Input.isQuit("exit"));
    }
}
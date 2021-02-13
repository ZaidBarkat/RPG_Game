import student.adventure.Input;
import student.adventure.State;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println(State.startState());
        System.out.println(State.goState(Input.userInput()));
        System.out.println(State.goState(Input.userInput()));
    }
}

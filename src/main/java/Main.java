import student.adventure.Input;
import student.adventure.adventureState;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(adventureState.startState());
        System.out.println(Input.isGo(Input.userInput()));
    }
}

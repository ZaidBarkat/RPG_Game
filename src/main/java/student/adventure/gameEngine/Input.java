package student.adventure.gameEngine;

import java.util.Scanner;

public class Input {

    public static String[] userInput() {
        final String PROMPT = ">";
        Scanner input = new Scanner(System.in);
        System.out.print(PROMPT);
        String[] inputString = input.nextLine().toLowerCase().split("\\W+");

        if ((inputString.length == 1 || inputString.length >= 3) && !(Input.isQuit(inputString[0])) && !(Input.isExamine(inputString[0]))) {
            return new String[]{" ", " "};
        }

        return inputString;
    }

    public static boolean isQuit(String input) {
        return input.contains("quit") || input.contains("exit");
    }

    public static boolean isGo(String input) {
        return input.contains("go");
    }

    public static boolean isExamine(String input) {
        return input.contains("examine");
    }

    public static boolean isTake(String input) {
        return input.contains("take");
    }

    public static boolean isDrop(String input) {
        return input.contains("drop");
    }
}

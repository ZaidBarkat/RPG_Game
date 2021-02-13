package student.adventure;

import java.util.Locale;
import java.util.Scanner;

public class Input {

    public static String userInput() {
        final String PROMPT = ">";
        Scanner input = new Scanner(System.in);
        System.out.print(PROMPT);
        return input.nextLine().toLowerCase().trim();
    }

    public static void isQuit(String input) {
        if (input.contains("exit") || input.contains("quit")) {
            System.exit(0);
        }
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

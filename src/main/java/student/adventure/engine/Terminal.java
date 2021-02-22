package student.adventure.engine;

import java.util.Scanner;

/** Used to establish the user's input and check what that input is. */
public class Terminal {
  /**
   * Prompt before any input, and turns input into string array to make it easier to check string.
   *
   * @return a string array of the user's input
   */
  public String getUserInput() {
    final String p = ">";
    Scanner input = new Scanner(System.in);
    System.out.print(p);

    return input.nextLine();
  }

  public String[] handleInput(String inputString) {
    String[] inputArray = inputString.toLowerCase().split("\\W+");

    if ((inputArray.length == 1 || inputArray.length >= 3)
            && !(inputArray[0].contains("quit") || inputArray[0].contains("exit"))
            && !inputArray[0].contains("examine")
            && !inputArray[0].contains("history")) {
      return new String[] {" "};
    }

    return inputArray;
  }
}

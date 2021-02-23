package student.adventure.engine;

import java.util.Scanner;

/** Used to establish the user's input and check what that input is. */
public class Terminal {
  /**
   * User input from the player
   *
   * @return a string of the user input
   */
  public String getUserInput() {
    final String p = ">";
    Scanner input = new Scanner(System.in);
    System.out.print(p);

    return input.nextLine();
  }

  /**
   * Handles the input given by the user, discarding if it is not designed for the run game method
   *
   * @param inputString the user input
   * @return an size 2 array with the command name and value
   */
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

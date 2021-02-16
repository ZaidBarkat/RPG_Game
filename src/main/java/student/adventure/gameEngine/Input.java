package student.adventure.gameEngine;

import java.util.Scanner;

/** Used to establish the user's input and check what that input is. */
public class Input {
  /**
   * Prompt before any input, and turns input into string array to make it easier to check string.
   *
   * @return a string array of the user's input
   */
  public static String[] userInput() {
    final String P = ">";
    Scanner input = new Scanner(System.in);
    System.out.print(P);
    String[] inputString = input.nextLine().toLowerCase().split("\\W+");

    if ((inputString.length == 1 || inputString.length >= 3)
        && !(Input.isQuit(inputString[0]))
        && !(Input.isExamine(inputString[0]))) {
      return new String[] {" ", " "};
    }

    return inputString;
  }

  /**
   * Checking if user input contains quit or exit keywords.
   *
   * @param input user input
   * @return boolean of user input
   */
  public static boolean isQuit(String input) {
    return input.contains("quit") || input.contains("exit");
  }

  /**
   * Checking if user input contains go keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public static boolean isGo(String input) {
    return input.contains("go");
  }

  /**
   * Checking if user input contains examine keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public static boolean isExamine(String input) {
    return input.contains("examine");
  }

  /**
   * Checking if user input contains take keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public static boolean isTake(String input) {
    return input.contains("take");
  }

  /**
   * Checking if user input contains drop keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public static boolean isDrop(String input) {
    return input.contains("drop");
  }
}

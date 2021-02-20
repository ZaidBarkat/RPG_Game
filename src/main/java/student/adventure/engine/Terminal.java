package student.adventure.engine;

import java.util.Scanner;

/** Used to establish the user's input and check what that input is. */
public class Terminal {
  /**
   * Prompt before any input, and turns input into string array to make it easier to check string.
   *
   * @return a string array of the user's input
   */
  public String userInput() {
    final String p = ">";
    Scanner input = new Scanner(System.in);
    System.out.print(p);
    
    return input.nextLine();
  }

  public String[] handleInput(String inputString) {
    Terminal terminal = new Terminal();

    String[] inputArray = inputString.toLowerCase().split("\\W+");

    if ((inputArray.length == 1 || inputArray.length >= 3)
            && !terminal.isQuit(inputArray[0])
            && !terminal.isExamine(inputArray[0])
            && !terminal.isHistory(inputArray[0])) {
      return new String[] {" "};
    }

    return inputArray;
  }

  /**
   * Checking if user input contains quit or exit keywords.
   *
   * @param input user input
   * @return boolean of user input
   */
  public boolean isQuit(String input) {
    return input.contains("quit") || input.contains("exit");
  }

  /**
   * Checking if user input contains go keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public boolean isGo(String input) {
    return input.contains("go");
  }

  /**
   * Checking if user input contains examine keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public boolean isExamine(String input) {
    return input.contains("examine");
  }

  /**
   * Checking if user input contains take keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public boolean isTake(String input) {
    return input.contains("take");
  }

  /**
   * Checking if user input contains drop keyword.
   *
   * @param input user input
   * @return boolean of user input
   */
  public boolean isDrop(String input) {
    return input.contains("drop");
  }

  public boolean isHistory(String input) {
    return input.contains("history");
  }

  public void terminalOutput(String result) {
    System.out.println(result);
  }
}

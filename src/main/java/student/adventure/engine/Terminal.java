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

  public void runGameFromTerminal(String[] inputArray, GameEngine gameEngine) {

    if (inputArray[0].contains("go")) {
      System.out.println(gameEngine.handleGoCommand(inputArray[1]));

    } else if (inputArray[0].contains("examine")) {
      System.out.println(gameEngine.handleExamineCommand());

    } else if (inputArray[0].contains("quit") || inputArray[0].contains("exit")) {
      gameEngine.setGameDone(true);

    } else if (inputArray[0].contains("take")) {
      System.out.println(gameEngine.handleTakeCommand(inputArray[1]));

    } else if (inputArray[0].contains("drop")) {
      System.out.println(gameEngine.handleDropCommand(inputArray[1]));

    } else if (inputArray[0].contains("history")) {
      System.out.println(gameEngine.handleHistoryCommand());
    }
  }
}

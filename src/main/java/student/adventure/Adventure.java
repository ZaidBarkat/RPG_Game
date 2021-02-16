package student.adventure;

import student.adventure.gameEngine.Input;
import student.adventure.gameEngine.State;

/** Runs the adventure engine. */
public class Adventure extends State {
  /** Method called when main function is run, to start the game. */
  public static void adventure() {
    startState();

    while (!State.winCondition()) {
      String[] input = Input.userInput();

      if (Input.isGo(input[0])) {
        goState(input);
      } else if (Input.isExamine(input[0])) {
        examineState();
      } else if (Input.isQuit(input[0])) {
        System.exit(0);
      } else if (Input.isTake(input[0])) {
        takeState(input);
      } else if (Input.isDrop(input[0])) {
        dropState(input);
      }
    }
  }
}

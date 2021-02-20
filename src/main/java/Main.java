import student.adventure.engine.Terminal;
import student.adventure.engine.GameEngine;

/** Runs main program, adventure game. */
public class Main {

  /**
   * Runs adventure game.
   *
   * @param args used to initialize main
   */
  public static void main(String[] args) {
    GameEngine gameEngine = new GameEngine();
    Terminal terminal = new Terminal();

    terminal.terminalOutput(gameEngine.startState());

    while (!gameEngine.isWinCondition()) {
      String[] inputArray = terminal.handleInput(terminal.userInput());

      gameEngine.runGameFromTerminal(inputArray);

      if (gameEngine.isGameDone()) {
        break;
      }
    }
  }
}

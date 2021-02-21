import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.engine.Terminal;
import student.adventure.engine.GameEngine;
import student.server.AdventureResource;
import student.server.AdventureServer;

import java.io.IOException;

/** Runs main program, adventure game. */
public class Main {

  /**
   * Runs adventure game.
   *
   * @param args used to initialize main
   */
  public static void main(String[] args) throws IOException {
    GameEngine gameEngine = new GameEngine();
    Terminal terminal = new Terminal();

    HttpServer server = AdventureServer.createServer(AdventureResource.class);
    server.start();

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

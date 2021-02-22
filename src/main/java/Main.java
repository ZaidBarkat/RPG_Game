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

    System.out.println(gameEngine.handleStartCommand());

    while (!gameEngine.isWinCondition()) {
      String[] inputArray = terminal.handleInput(terminal.getUserInput());

      System.out.println(gameEngine.runGame(inputArray, gameEngine));

      if (gameEngine.isGameDone()) {
        break;
      }
    }
  }
}

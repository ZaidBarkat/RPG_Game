package student.server;

import student.adventure.engine.GameEngine;
import student.adventure.pojo.Room;
import student.server.AdventureException;
import student.server.AdventureService;
import student.server.Command;
import student.server.GameStatus;

import java.lang.reflect.Array;
import java.util.*;

public class PrisonAdventure implements AdventureService {
  private int idValue = -1;
  private ArrayList<GameEngine> gameEngines = new ArrayList<>();
  private String[] commands = new String[2];

  /** Resets the service to its initial state. */
  @Override
  public void reset() {
    idValue = -1;
    gameEngines.clear();
  }

  /**
   * Creates a new Adventure game and stores it.
   *
   * @return the id of the game.
   */
  @Override
  public int newGame() throws AdventureException {
    idValue++;
    GameEngine gameEngine = new GameEngine(idValue);
    gameEngine.handleStartCommand();
    gameEngines.add(gameEngine);

    if (!(idValue >= 0)) {
      throw new AdventureException("unknown ID");
    }

    return idValue;
  }

  /**
   * Returns the state of the game instance associated with the given ID.
   *
   * @param id the instance id
   * @return the current state of the game
   */
  @Override
  public GameStatus getGame(int id) {
    boolean exception = false;
    String message;
    String sound = "https://www.youtube.com/watch?v=ipNqY9wHPqg";
    GameEngine gameEngine = findByGameEngineId(gameEngines, id);
    HashMap<String, List<String>> commandOptions = new HashMap<>();
    List<String> path = new ArrayList<>();

    try {
      if (commands[0] != null && commands[0].equals("Traversed")) {
        message = gameEngine.getRoom().getDescription() + "\n" + gameEngine.handleHistoryCommand();
      } else {
        message = gameEngine.getRoom().getDescription();
      }
    } catch (Exception e) {
      exception = true;
      message = "";
    }

    path.add("path");

    commandOptions.put("go", gameEngine.getDirectionNames());
    commandOptions.put("Traversed", path);
    commandOptions.put("take", gameEngine.getRoom().getItems());
    commandOptions.put("drop", gameEngine.getInventory());

    if (gameEngine.isWinCondition()) {
      sound = null;
      commandOptions = new HashMap<>();
    }

    return new GameStatus(
        exception,
        id,
        message,
        gameEngine.getRoom().getImage(),
        sound,
        new AdventureState(),
        commandOptions);
  }

  /**
   * Removes & destroys a game instance with the given ID.
   *
   * @param id the instance id
   * @return false if the instance could not be found and/or was not deleted
   */
  @Override
  public boolean destroyGame(int id) {
    if (findByGameEngineId(gameEngines, id) == null) {
      return false;
    }

    GameEngine gameEngine = findByGameEngineId(gameEngines, id);

    gameEngines.remove(gameEngine);
    return true;
  }

  /**
   * Executes a command on the game instance with the given id, changing the game state if
   * applicable.
   *
   * @param id the instance id
   * @param command the issued command
   */
  @Override
  public void executeCommand(int id, Command command) {
    GameEngine gameEngine = findByGameEngineId(gameEngines, id);

    commands[0] = command.getCommandName();
    commands[1] = command.getCommandValue();

    gameEngine.runGame(commands, gameEngine);
  }

  /**
   * Returns a sorted leaderboard of player "high" scores.
   *
   * @return a sorted map of player names to scores
   */
  @Override
  public SortedMap<String, Integer> fetchLeaderboard() {
    return null;
  }



  public int getIdValue() {
    return idValue;
  }

  public ArrayList<GameEngine> getGameEngines() {
    return gameEngines;
  }
}

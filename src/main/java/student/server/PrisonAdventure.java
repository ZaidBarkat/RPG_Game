package student.server;

import student.adventure.engine.GameEngine;
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
    //Increments idValue, and then sets a new GameEngine instance with that idValue
    idValue++;
    GameEngine gameEngine = new GameEngine(idValue);
    gameEngine.handleStartCommand();
    gameEngines.add(gameEngine);

    if (idValue < 0) {
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

    if (id < 0) {
      throw new IllegalArgumentException("Unknown Id");
    }
    //Finds the gameEngine based on the list and id of the gameEngines
    GameEngine gameEngine = GameEngine.findByGameEngineId(gameEngines, id);
    HashMap<String, List<String>> commandOptions = new HashMap<>();
    List<String> path = new ArrayList<>();
    String sound = gameEngine.getLayout().getVideoUrl();

    //Try/catch to make sure that gameEngine is not null
    try {
      if (commands[0] != null && commands[0].equals("traversed")) {
        message = gameEngine.getRoom().getDescription() + "\n" + gameEngine.handleHistoryCommand();
      } else {
        message = gameEngine.getRoom().getDescription();
      }
    } catch (Exception e) {
      exception = true;
      message = "";
    }

    //Needed to show the Traversed button on the website
    path.add("path");

    //Filling map with commands from the gameEngine
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
    if (id < 0) {
      throw new IllegalArgumentException();
    }

    if (GameEngine.findByGameEngineId(gameEngines, id) == null) {
      return false;
    }

    GameEngine gameEngine = GameEngine.findByGameEngineId(gameEngines, id);

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
    if (id < 0) {
      throw new IllegalArgumentException("Invalid Id");
    }

    GameEngine gameEngine = GameEngine.findByGameEngineId(gameEngines, id);

    commands[0] = command.getCommandName().toLowerCase();
    commands[1] = command.getCommandValue().toLowerCase();

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

  public ArrayList<GameEngine> getGameEngines() {
    return gameEngines;
  }
}

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

    /**
     * Resets the service to its initial state.
     */
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
        GameEngine gameEngine = new GameEngine(idValue++);
        gameEngine.startState();
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
        String message = "";
        GameEngine gameEngine = findByGameEngineId(gameEngines, id);
        AdventureState adventureState = new AdventureState();
        HashMap<String, List<String>> commandOptions = new HashMap<>();

        try{
            message = gameEngine.getRoom().getDescription();
        } catch(Exception e) {
            exception = true;
        }

        commandOptions.put("go", gameEngine.getDirectionNames());
        commandOptions.put("history", new ArrayList<>());
        commandOptions.put("take", gameEngine.getRoom().getItems());
        commandOptions.put("drop", gameEngine.getInventory());

        return new GameStatus(exception, id, message, null, null, adventureState, commandOptions);
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
     * Executes a command on the game instance with the given id, changing the game state if applicable.
     *
     * @param id      the instance id
     * @param command the issued command
     */
    @Override
    public void executeCommand(int id, Command command) {
        GameEngine gameEngine = findByGameEngineId(gameEngines, id);

        switch (command.getCommandName()) {
            case "go":
                gameEngine.goState(command.getCommandValue());
                break;
            case "history":
                gameEngine.historyState();
                break;
            case "take":
                gameEngine.takeState(command.getCommandValue());
                break;
            case "drop":
                gameEngine.dropState(command.getCommandValue());
                break;
        }
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

    private GameEngine findByGameEngineId(Collection<GameEngine> gameEngine, int id) {
        return gameEngine.stream()
                .filter(game -> id == game.getInstanceId())
                .findFirst()
                .orElse(null);
    }
}

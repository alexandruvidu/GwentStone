package gamelogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;

/**
 * Parser for games.
 */
public final class GameParser {

    private static ArrayNode output;

    private int player1Wins = 0;

    private int player2Wins = 0;

    /**
     * Constructor, initializes the output node.
     */
    public GameParser() {
        final ObjectMapper objectMapper = new ObjectMapper();
        output = objectMapper.createArrayNode();
    }

    /**
     * Iterates over every game and creates a Game instance.
     *
     * @param input given as an input from a file in main.Main
     */
    public void parseGames(final Input input) {
        for (int i = 0; i < input.getGames().size(); ++i) {
            final Game game = new Game(this);
            game.startGame(input, i);
        }
    }

    public static ArrayNode getOutput() {
        return output;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    /**
     * Adds a win to player1.
     */
    public void addPlayer1Win() {
        this.player1Wins++;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    /**
     * Adds a win to player2.
     */
    public void addPlayer2Win() {
        this.player2Wins++;
    }

    /**
     * Gets number of games played.
     *
     * @return sum of wins of both players
     */
    public int getGamesPlayed() {
        return player1Wins + player2Wins;
    }
}

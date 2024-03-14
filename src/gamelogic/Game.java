package gamelogic;

import cards.CardFactory;
import cards.Minion;
import fileio.ActionsInput;
import fileio.Coordinates;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;
import gamelogic.actions.ActionParser;
import gamelogic.actions.ActionsEnum;
import gamelogic.elements.Player;
import gamelogic.elements.Table;

import java.util.Collections;
import java.util.Random;

public final class Game {

    private final GameParser gameParser;

    private final int maxMana = 10;

    private Table table;

    private Player player1;

    private Player player2;

    private Player whoseTurn;

    /**
     * Mana value to be given to the player at the end of the round.
     */
    private int turnMana = 1;

    /**
     * Number of turns played.
     */
    private int turns = 0;

    public Game(final GameParser gameParser) {
        this.gameParser = gameParser;
    }

    /**
     * Initializes game fields, shuffles the decks and starts the game by iterating through actions
     *
     * @param in contains information needed for initializing fields and start a game
     * @param index number of game in input
     */
    public void startGame(final Input in, final int index) {
        final StartGameInput startIn = in.getGames().get(index).getStartGame();
        player1 = new Player(in.getPlayerOneDecks().getDecks().get(startIn.getPlayerOneDeckIdx()),
                startIn.getPlayerOneHero(), 2, 3);
        player2 = new Player(in.getPlayerTwoDecks().getDecks().get(startIn.getPlayerTwoDeckIdx()),
                startIn.getPlayerTwoHero(), 1, 0);
        table = new Table();

        Collections.shuffle(player1.getDeck(), new Random(startIn.getShuffleSeed()));
        Collections.shuffle(player2.getDeck(), new Random(startIn.getShuffleSeed()));

        final GameInput gameInput = in.getGames().get(index);

        final int startingPlayer = gameInput.getStartGame().getStartingPlayer();
        if (startingPlayer == 1) {
            whoseTurn = player1;
        } else {
            whoseTurn = player2;
        }

        player1.drawCard();
        player2.drawCard();

        final ActionParser actionParser = new ActionParser(this, gameParser);
        for (final ActionsInput action : gameInput.getActions()) {
            actionParser.doActions(action);
            if (GameParser.getOutput().size() > 0 && GameParser.getOutput()
                    .get(GameParser.getOutput().size() - 1).has(ActionsEnum.GAMEENDED.getName())) {
                break;
            }
        }
    }

    public Table getTable() {
        return table;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWhoseTurn() {
        return whoseTurn;
    }

    public int getTurns() {
        return turns;
    }

    /**
     * Get whether the current player has tanks or not.
     *
     * @return calls the hasTanks method in the current player
     */
    public boolean enemyHasTanks() {
        if (whoseTurn == player1) {
            return player2.hasTanks();
        }
        return player1.hasTanks();
    }

    /**
     * Does all the necessary things after an end of turn: <br>
     * - unfreezes and resets attacks of current player's minions, <br>
     * - changes the current player <br>
     * - increments the turns played
     */
    public void endTurn() {
        table.unfreezeCardsOnRow(whoseTurn.getBackRow());
        table.unfreezeCardsOnRow(whoseTurn.getFrontRow());

        table.resetAttacksOnRow(whoseTurn.getBackRow());
        table.resetAttacksOnRow(whoseTurn.getFrontRow());
        player1.getHero().setHasAttacked(false);
        player2.getHero().setHasAttacked(false);

        if (whoseTurn == player1) {
            whoseTurn = player2;
        } else {
            whoseTurn = player1;
        }

        this.turns++;
    }

    /**
     * At the end of a round, each player draws a card and recieves mana.
     */
    public void endRound() {
        player1.drawCard();
        player2.drawCard();
        if (turnMana < maxMana) {
            turnMana++;
        }
        player1.setMana(player1.getMana() + turnMana);
        player2.setMana(player2.getMana() + turnMana);
    }

    /**
     * Places a minion on the table on its assigned row.
     *
     * @param minion minion to be placed on the table
     */
    public void placeCard(final Minion minion) {
        final CardFactory cardFactory = new CardFactory();
        final Minion newMinion = (Minion) cardFactory.duplicateCard(minion);
        if (minion.getRow() == 0) {
            table.addCardOnRow(newMinion, whoseTurn.getBackRow());
        } else {
            table.addCardOnRow(newMinion, whoseTurn.getFrontRow());
        }
        whoseTurn.substractMana(minion.getCost());
        if (minion.isTank()) {
            whoseTurn.addTank();
        }
    }

    /**
     * The attacker minion deals damage to the attacked minion.
     *
     * @param attackerCoords coordinates of the minion that attacks
     * @param attackedCoords coordinates of the minion that is attacked
     */
    public void minionAttack(final Coordinates attackerCoords, final Coordinates attackedCoords) {
        final Minion attackerMinion = table.getCardFromTable(attackerCoords);
        final Minion attackedMinion = table.getCardFromTable(attackedCoords);
        if (attackerMinion.getAttackDamage() >= attackedMinion.getHealth()) {
            if (attackedMinion.isTank()) {
                if (whoseTurn == player1) {
                    player2.removeTank();
                } else {
                    player1.removeTank();
                }
            }
            table.deleteCard(attackedCoords);
        } else {
            attackedMinion.takeDamage(attackerMinion.getAttackDamage());
        }
    }
}

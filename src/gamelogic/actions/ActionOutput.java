package gamelogic.actions;

import cards.AbstractCard;
import cards.CardFactory;
import cards.Minion;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.Coordinates;
import gamelogic.elements.Table;

import java.util.ArrayList;

/**
 * Output builder class for json outputs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ActionOutput {

    private final CardFactory cardFactory = new CardFactory();

    private String command = null;

    private Integer handIdx = null;

    private Coordinates cardAttacker = null;

    private Coordinates cardAttacked = null;

    private Integer affectedRow = null;

    private Integer playerIdx = null;

    @JsonProperty("output")
    private ArrayList<AbstractCard> outputList = null;

    @JsonProperty("output")
    private ArrayList<ArrayList<Minion>> outputTable = null;

    @JsonProperty("output")
    private Integer outputInt = null;

    @JsonProperty("output")
    private AbstractCard outputCard = null;

    @JsonProperty("output")
    private String outputString;

    private String error = null;

    private String gameEnded = null;

    private Integer x = null;

    private Integer y = null;

    /**
     * Constructor used for placeCard command.
     */
    public ActionOutput(final String command, final Integer handIdx, final String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.error = error;
    }

    /**
     * Constructor used for cardUsesAttack and cardUsesAbility commands.
     */
    public ActionOutput(final String command, final Coordinates cardAttacker,
                        final Coordinates cardAttacked, final String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.cardAttacked = cardAttacked;
        this.error = error;
    }

    /**
     * Constructor used for useAttackHero command.
     */
    public ActionOutput(final String command, final Coordinates cardAttacker,
                        final String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }

    /**
     * Constructor used for useAttackHero command if the enemy hero has been killed.
     */
    public ActionOutput(final String gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     * Constructor used for useHeroAbility command.
     */
    public ActionOutput(final String command, final String error, final Integer affectedRow) {
        this.command = command;
        this.error = error;
        this.affectedRow = affectedRow;
    }

    /**
     * Constructor used for useEnvironmentCard command.
     */
    public ActionOutput(final String command, final Integer handIdx,
                        final Integer affectedRow, final String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    /**
     * Constructor used for getCardsInHand, getPlayerDeck and getEnvironmentCardsInHand commands.
     */
    public ActionOutput(final String command, final Integer playerIdx,
                        final ArrayList<AbstractCard> outputList) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.outputList = new ArrayList<>();
        for (final AbstractCard card : outputList) {
            this.outputList.add(cardFactory.duplicateCard(card));
        }
    }

    /**
     * Constructor used for getFrozenCardsOnTable command.
     */
    public ActionOutput(final String command, final ArrayList<AbstractCard> outputList) {
        this.command = command;
        this.outputList = new ArrayList<>();
        for (final AbstractCard card : outputList) {
            this.outputList.add(cardFactory.duplicateCard(card));
        }
    }

    /**
     * Constructor used for getPlayerTurn, getTotalGamesPlayed and getPlayerWins commands.
     */
    public ActionOutput(final String command, final Integer outputInt) {
        this.command = command;
        this.outputInt = outputInt;
    }

    /**
     * Constructor used for getPlayerHero command.
     */
    public ActionOutput(final String command, final AbstractCard outputCard,
                        final Integer playerIdx) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.outputCard = cardFactory.duplicateCard(outputCard);
    }

    /**
     * Constructor used for getCardAtPosition command.
     */
    public ActionOutput(final String command, final Integer x, final Integer y,
                        final AbstractCard outputCard) {
        this.command = command;
        this.outputCard = cardFactory.duplicateCard(outputCard);
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor used for getCardAtPosition command with an error.
     */
    public ActionOutput(final String command, final String outputString,
                        final Integer x, final Integer y) {
        this.command = command;
        this.outputString = outputString;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor used for getPlayerMana command.
     */
    public ActionOutput(final String command, final Integer playerIdx,
                        final Integer outputInt) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.outputInt = outputInt;
    }

    /**
     * Constructor used for getCardsOnTable command.
     */
    public ActionOutput(final ArrayList<ArrayList<Minion>> outputTable,
                        final String command) {
        this.command = command;
        this.outputTable = new ArrayList<>();
        for (int i = 0; i < Table.getNumberOfRows(); ++i) {
            this.outputTable.add(new ArrayList<>());
        }
        for (int i = 0; i < Table.getNumberOfRows(); ++i) {
            for (int j = 0; j < outputTable.get(i).size(); ++j) {
                final Minion minion = outputTable.get(i).get(j);
                this.outputTable.get(i).add((Minion) cardFactory.duplicateCard(minion));
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public Integer getHandIdx() {
        return handIdx;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public Integer getAffectedRow() {
        return affectedRow;
    }

    public Integer getPlayerIdx() {
        return playerIdx;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    /**
     * @return the corresponding output for property "output" needed for creating the json output
     */
    @JsonProperty("output")
    public Object getOutput() {
        if (outputList != null) {
            return outputList;
        }
        if (outputCard != null) {
            return outputCard;
        }
        if (outputTable != null) {
            return outputTable;
        }
        if (outputString != null) {
            return outputString;
        }
        return outputInt;
    }

    public String getError() {
        return error;
    }

    public String getGameEnded() {
        return gameEnded;
    }
}

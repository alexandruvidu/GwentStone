package gamelogic.actions;

import cards.AbstractCard;
import cards.Enums.CardsEnum;
import cards.Enums.EnvironmentsEnum;
import cards.Enums.HeroesEnum;
import cards.Enums.MinionsEnum;
import cards.Environment;
import cards.Hero;
import cards.Minion;
import fileio.ActionsInput;
import fileio.Coordinates;
import gamelogic.Game;
import gamelogic.GameParser;
import gamelogic.elements.Player;
import gamelogic.elements.Table;

import java.util.ArrayList;

/**
 * Parser for actions.
 * For every gameplay action, first there are validity checks and
 * if the parameters are not valid an error is added in the output.
 */
public final class ActionParser {
    private final GameParser gameParser;
    private final Game gameInstance;

    public ActionParser(final Game gameInstance, final GameParser gameParser) {
        this.gameInstance = gameInstance;
        this.gameParser = gameParser;
    }

    /**
     * Executes an action based on its name and calls the appropriate function.
     *
     * @param action action to be executed
     */
    public void doActions(final ActionsInput action) {
        if (action.getCommand().equals(ActionsEnum.ENDPLAYERTURN.getName())) {
            endPlayerTurn();
        } else if (action.getCommand().equals(ActionsEnum.PLACECARD.getName())) {
            placeCard(action.getHandIdx());
        } else if (action.getCommand().equals(ActionsEnum.CARDUSESATTACK.getName())) {
            cardUsesAttackOrAbility(action.getCardAttacker(), action.getCardAttacked(), 0);
        } else if (action.getCommand().equals(ActionsEnum.CARDUSESABILITY.getName())) {
            cardUsesAttackOrAbility(action.getCardAttacker(), action.getCardAttacked(), 1);
        } else if (action.getCommand().equals(ActionsEnum.USEATTACKHERO.getName())) {
            useAttackHero(action.getCardAttacker());
        } else if (action.getCommand().equals(ActionsEnum.USEHEROABILITY.getName())) {
            useHeroAbility(action.getAffectedRow());
        } else if (action.getCommand().equals(ActionsEnum.USEENVIRONMENTCARD.getName())) {
            useEnvironmentCard(action.getHandIdx(), action.getAffectedRow());
        } else if (action.getCommand().equals(ActionsEnum.GETCARDSINHAND.getName())) {
            getCardsInHand(action.getPlayerIdx());
        } else if (action.getCommand().equals(ActionsEnum.GETPLAYERDECK.getName())) {
            getPlayerDeck(action.getPlayerIdx());
        } else if (action.getCommand().equals(ActionsEnum.GETCARDSONTABLE.getName())) {
            getCardsOnTable();
        } else if (action.getCommand().equals(ActionsEnum.GETPLAYERTURN.getName())) {
            getPlayerTurn();
        } else if (action.getCommand().equals(ActionsEnum.GETPLAYERHERO.getName())) {
            getPlayerHero(action.getPlayerIdx());
        } else if (action.getCommand().equals(ActionsEnum.GETCARDATPOSITION.getName())) {
            getCardAtPosition(action.getX(), action.getY());
        } else if (action.getCommand().equals(ActionsEnum.GETPLAYERMANA.getName())) {
            getPlayerMana(action.getPlayerIdx());
        } else if (action.getCommand().equals(ActionsEnum.GETENVIRONMENTCARDSINHAND.getName())) {
            getEnvironmentCardsInHand(action.getPlayerIdx());
        } else if (action.getCommand().equals(ActionsEnum.GETFROZENCARDSONTABLE.getName())) {
            getFrozenCardsOnTable();
        } else if (action.getCommand().equals(ActionsEnum.GETTOTALGAMESPLAYED.getName())) {
            getTotalGamesPlayed();
        } else if (action.getCommand().equals(ActionsEnum.GETPLAYERONEWINS.getName())) {
            getPlayerWins(0);
        } else if (action.getCommand().equals(ActionsEnum.GETPLAYERTWOWINS.getName())) {
            getPlayerWins(1);
        }
    }

    /**
     * Ends a turn and if the turn number is even ends the round.
     */
    private void endPlayerTurn() {
        gameInstance.endTurn();
        if (gameInstance.getTurns() % 2 == 0) {
            gameInstance.endRound();
        }
    }

    /**
     * Places a card form the current player's hand.
     *
     * @param index number of card in hand
     */
    private void placeCard(final int index) {
        final AbstractCard card = gameInstance.getWhoseTurn().getHand().get(index);
        String error = null;
        final Player currentTurn = gameInstance.getWhoseTurn();

        if (card.getCardType() == CardsEnum.ENVIRONMENT) {
            error = "Cannot place environment card on table.";
        } else if (gameInstance.getWhoseTurn().getMana() < card.getCost()) {
            error = "Not enough mana to place card on table.";
        } else {
            final int tableRow;
            final Minion minion = (Minion) card;

            if (minion.getRow() == 0) {
                tableRow = currentTurn.getBackRow();
            } else {
                tableRow = currentTurn.getFrontRow();
            }

            if (gameInstance.getTable().isRowFull(tableRow)) {
                error = "Cannot place card on table since row is full.";
            }

        }
        if (error != null) {
            GameParser.getOutput()
                    .addPOJO(new ActionOutput(ActionsEnum.PLACECARD.getName(), index, error));
        } else {
            final Minion minion = (Minion) currentTurn.getHand().get(index);
            gameInstance.placeCard(minion);
            currentTurn.getHand().remove(index);
        }

    }

    /**
     * Executes the attack/ability of a minion.
     *
     * @param attackerCoords coordinates of the attacker minion
     * @param attackedCoords coordinates of the attacked minion
     * @param action 0 for cardUsesAttack, 1 for cardUsesAbility
     */
    private void cardUsesAttackOrAbility(final Coordinates attackerCoords,
                                         final Coordinates attackedCoords, final int action) {
        String error = null;

        if (gameInstance.getTable().isCardFrozen(attackerCoords)) {
            error = "Attacker card is frozen.";
        } else if (gameInstance.getTable().cardHasAttacked(attackerCoords)) {
            error = "Attacker card has already attacked this turn.";
        }

        if (action == 0) {
            if (!gameInstance.getTable().isEnemy(gameInstance.getWhoseTurn(), attackedCoords)) {
                error = "Attacked card does not belong to the enemy.";
            } else if (gameInstance.enemyHasTanks()
                    && !gameInstance.getTable().getCardFromTable(attackedCoords).isTank()) {
                error = "Attacked card is not of type 'Tank'.";
            }
        } else if (action == 1) {
            final MinionsEnum minionType =
                    gameInstance.getTable().getCardFromTable(attackerCoords).getMinionType();
            if (minionType == MinionsEnum.DISCIPLE
                && gameInstance.getTable().isEnemy(gameInstance.getWhoseTurn(), attackedCoords)) {
                error = "Attacked card does not belong to the current player.";
            } else if (minionType == MinionsEnum.THERIPPER || minionType == MinionsEnum.MIRAJ
                    || minionType == MinionsEnum.THECURSEDONE) {
                if (!gameInstance.getTable().isEnemy(gameInstance.getWhoseTurn(),
                                                        attackedCoords)) {
                    error = "Attacked card does not belong to the enemy.";
                } else if (gameInstance.enemyHasTanks()
                        && !gameInstance.getTable().getCardFromTable(attackedCoords).isTank()) {
                    error = "Attacked card is not of type 'Tank'.";
                }
            }
        }

        if (error != null) {
            final String command;

            if (action == 0) {
                command = ActionsEnum.CARDUSESATTACK.getName();
            } else {
                command = ActionsEnum.CARDUSESABILITY.getName();
            }

            GameParser.getOutput()
                    .addPOJO(new ActionOutput(command, attackerCoords, attackedCoords, error));
        } else {
            final Minion attacker = gameInstance.getTable().getCardFromTable(attackerCoords);
            attacker.setHasAttacked(true);

            if (action == 0) {
                gameInstance.minionAttack(attackerCoords, attackedCoords);
            } else if (action == 1) {
                attacker.ability(gameInstance, attackedCoords);
            }
        }
    }

    /**
     * Executes an attack on the enemy hero.
     *
     * @param attackerCoords coordinates of the attacker minion
     */
    private void useAttackHero(final Coordinates attackerCoords) {
        String out = null;

        if (gameInstance.getTable().isCardFrozen(attackerCoords)) {
            out = "Attacker card is frozen.";
        } else if (gameInstance.getTable().cardHasAttacked(attackerCoords)) {
            out = "Attacker card has already attacked this turn.";
        } else if (gameInstance.enemyHasTanks()) {
            out = "Attacked card is not of type 'Tank'.";
        }

        if (out != null) {
            GameParser.getOutput()
                    .addPOJO(new ActionOutput(ActionsEnum.USEATTACKHERO.getName(),
                            attackerCoords, out));
        } else {
            final Player otherTurn;

            if (gameInstance.getWhoseTurn() == gameInstance.getPlayer1()) {
                otherTurn = gameInstance.getPlayer2();
            } else {
                otherTurn = gameInstance.getPlayer1();
            }

            final Minion attackerMinion = gameInstance.getTable().getCardFromTable(attackerCoords);
            attackerMinion.setHasAttacked(true);

            if (attackerMinion.getAttackDamage() >= otherTurn.getHero().getHealth()) {
                if (gameInstance.getWhoseTurn() == gameInstance.getPlayer1()) {
                    out = "Player one killed the enemy hero.";
                    gameParser.addPlayer1Win();
                } else {
                    out = "Player two killed the enemy hero.";
                    gameParser.addPlayer2Win();
                }
                GameParser.getOutput().addPOJO(new ActionOutput(out));
            } else {
                otherTurn.getHero().takeDamage(attackerMinion.getAttackDamage());
            }
        }
    }

    /**
     * Executes the ability of the current player's hero.
     *
     * @param affectedRow row on which to use the ability
     */
    private void useHeroAbility(final int affectedRow) {
        String error = null;
        final Player currentTurn = gameInstance.getWhoseTurn();
        if (currentTurn.getHero().getCost() > currentTurn.getMana()) {
            error = "Not enough mana to use hero's ability.";
        } else if (currentTurn.getHero().hasAttacked()) {
            error = "Hero has already attacked this turn.";
        } else {
            final HeroesEnum heroType = currentTurn.getHero().getHeroType();
            if (heroType == HeroesEnum.LORDROYCE || heroType == HeroesEnum.EMPRESSTHORINA) {
                if (affectedRow == currentTurn.getFrontRow()
                        || affectedRow == currentTurn.getBackRow()) {
                    error = "Selected row does not belong to the enemy.";
                }
            } else {
                if (affectedRow != currentTurn.getFrontRow()
                        && affectedRow != currentTurn.getBackRow()) {
                    error = "Selected row does not belong to the current player.";
                }
            }
        }
        if (error != null) {
            GameParser.getOutput()
                    .addPOJO(new ActionOutput(ActionsEnum.USEHEROABILITY.getName(),
                            error, affectedRow));
        } else {
            currentTurn.getHero().setHasAttacked(true);
            currentTurn.getHero().ability(gameInstance.getTable(), affectedRow);
            currentTurn.substractMana(currentTurn.getHero().getCost());
        }


    }

    /**
     * Plays an environment card from the current player's hand.
     *
     * @param handIdx number of card in hand
     * @param affectedRow row on which to use the card
     */
    private void useEnvironmentCard(final int handIdx, final int affectedRow) {
        String error = null;
        final AbstractCard card = gameInstance.getWhoseTurn().getHand().get(handIdx);
        if (card.getCardType() != CardsEnum.ENVIRONMENT) {
            error = "Chosen card is not of type environment.";
        } else if (card.getCost() > gameInstance.getWhoseTurn().getMana()) {
            error = "Not enough mana to use environment card.";
        } else if (affectedRow == gameInstance.getWhoseTurn().getBackRow()
                || affectedRow == gameInstance.getWhoseTurn().getFrontRow()) {
            error = "Chosen row does not belong to the enemy.";
        } else {
            final Environment envCard = (Environment) card;
            if (envCard.getEnvironmentType() == EnvironmentsEnum.HEARTHOUND) {
                final int oppositeBackRow;
                final int oppositeFrontRow;
                if (gameInstance.getWhoseTurn() == gameInstance.getPlayer1()) {
                    oppositeBackRow = gameInstance.getPlayer2().getBackRow();
                    oppositeFrontRow = gameInstance.getPlayer2().getFrontRow();
                } else {
                    oppositeBackRow = gameInstance.getPlayer1().getBackRow();
                    oppositeFrontRow = gameInstance.getPlayer1().getFrontRow();
                }
                final Player currentTurn = gameInstance.getWhoseTurn();
                final Table table = gameInstance.getTable();
                if (affectedRow == oppositeBackRow
                        && table.isRowFull(currentTurn.getBackRow())
                    || affectedRow == oppositeFrontRow
                        && table.isRowFull(currentTurn.getFrontRow())) {
                    error = "Cannot steal enemy card since the player's row is full.";
                }
            }
        }
        if (error != null) {
            GameParser.getOutput()
                    .addPOJO(new ActionOutput(ActionsEnum.USEENVIRONMENTCARD.getName(),
                            handIdx, affectedRow, error));
        } else {
            final Environment envCard = (Environment) card;
            envCard.ability(gameInstance, affectedRow);
            gameInstance.getWhoseTurn().substractMana(envCard.getCost());
            gameInstance.getWhoseTurn().getHand().remove(handIdx);
        }
    }

    /**
     * Outputs a player's hand.
     *
     * @param playerIdx player number
     */
    private void getCardsInHand(final int playerIdx) {
        final ArrayList<AbstractCard> hand;
        if (playerIdx == 1) {
            hand = gameInstance.getPlayer1().getHand();
        } else {
            hand = gameInstance.getPlayer2().getHand();
        }
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETCARDSINHAND.getName(), playerIdx, hand));
    }

    /**
     * Outputs a player's deck.
     *
     * @param playerIdx player number
     */
    private void getPlayerDeck(final int playerIdx) {
        final ArrayList<AbstractCard> deck;
        if (playerIdx == 1) {
            deck = gameInstance.getPlayer1().getDeck();
        } else {
            deck = gameInstance.getPlayer2().getDeck();
        }
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETPLAYERDECK.getName(), playerIdx, deck));
    }

    /**
     * Outputs all minions on the table.
     */
    private void getCardsOnTable() {
        final ArrayList<ArrayList<Minion>> list = gameInstance.getTable().getAllCards();
        GameParser.getOutput()
                .addPOJO(new ActionOutput(list, ActionsEnum.GETCARDSONTABLE.getName()));
    }

    /**
     * Outputs which player's turn it is.
     */
    private void getPlayerTurn() {
        final int whoseTurn;
        if (gameInstance.getWhoseTurn() == gameInstance.getPlayer1()) {
            whoseTurn = 1;
        } else {
            whoseTurn = 2;
        }
        GameParser.getOutput()
            .addPOJO(new ActionOutput(ActionsEnum.GETPLAYERTURN.getName(), whoseTurn));
    }

    /**
     * Outputs a player's hero.
     *
     * @param playerIdx player number
     */
    private void getPlayerHero(final int playerIdx) {
        final Hero hero;
        if (playerIdx == 1) {
            hero = gameInstance.getPlayer1().getHero();
        } else {
            hero = gameInstance.getPlayer2().getHero();
        }
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETPLAYERHERO.getName(), hero, playerIdx));
    }

    /**
     * Outputs a card at a certain position.
     *
     * @param row row on the table
     * @param column column on the table
     */
    private void getCardAtPosition(final int row, final int column) {
        if (gameInstance.getTable().getRows().get(row).size() >= column) {
            final Coordinates coords = new Coordinates();
            coords.setX(row);
            coords.setY(column);
            GameParser.getOutput()
                    .addPOJO(new ActionOutput(ActionsEnum.GETCARDATPOSITION.getName(),
                            row, column, gameInstance.getTable().getCardFromTable(coords)));
        } else {
            GameParser.getOutput()
                    .addPOJO(new ActionOutput(ActionsEnum.GETCARDATPOSITION.getName(),
                            "No card available at that position.", row, column));
        }
    }

    /**
     * Outputs a player's mana.
     *
     * @param playerIdx player number
     */
    private void getPlayerMana(final int playerIdx) {
        final int mana;
        if (playerIdx == 1) {
            mana = gameInstance.getPlayer1().getMana();
        } else {
            mana = gameInstance.getPlayer2().getMana();
        }
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETPLAYERMANA.getName(), playerIdx, mana));
    }

    /**
     * Outputs a player's environment cards in hand.
     *
     * @param playerIdx player number
     */
    private void getEnvironmentCardsInHand(final int playerIdx) {
        final ArrayList<AbstractCard> list = new ArrayList<>();
        final ArrayList<AbstractCard> hand;
        if (playerIdx == 1) {
            hand = gameInstance.getPlayer1().getHand();
        } else {
            hand = gameInstance.getPlayer2().getHand();
        }

        for (final AbstractCard card : hand) {
            if (card.getCardType() == CardsEnum.ENVIRONMENT) {
                list.add(card);
            }
        }
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETENVIRONMENTCARDSINHAND.getName(),
                        playerIdx, list));
    }

    /**
     * Outputs all frozen cards on table.
     */
    private void getFrozenCardsOnTable() {
        final ArrayList<AbstractCard> list = gameInstance.getTable().getAllFrozenCards();
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETFROZENCARDSONTABLE.getName(), list));
    }

    /**
     * Outputs the total number of games played.
     */
    private void getTotalGamesPlayed() {
        GameParser.getOutput()
                .addPOJO(new ActionOutput(ActionsEnum.GETTOTALGAMESPLAYED.getName(),
                        gameParser.getGamesPlayed()));
    }

    /**
     * Outputs a player's number of wins.
     *
     * @param playerIdx player number
     */
    private void getPlayerWins(final int playerIdx) {
        final int winNumber;
        final String command;
        if (playerIdx == 0) {
            winNumber = gameParser.getPlayer1Wins();
            command = ActionsEnum.GETPLAYERONEWINS.getName();
        } else {
            winNumber = gameParser.getPlayer2Wins();
            command = ActionsEnum.GETPLAYERTWOWINS.getName();
        }
        GameParser.getOutput().addPOJO(new ActionOutput(command, winNumber));
    }
}

package gamelogic.elements;

import cards.AbstractCard;
import cards.Minion;
import fileio.Coordinates;

import java.util.ArrayList;

public final class Table {

    private final ArrayList<ArrayList<Minion>> rows;

    private static final int MAXCARDSONROW = 5;

    private static final int NUMBEROFROWS = 4;

    public Table() {
        rows = new ArrayList<>();
        for (int i = 0; i < NUMBEROFROWS; ++i) {
            rows.add(new ArrayList<>());
        }
    }

    public ArrayList<ArrayList<Minion>> getRows() {
        return rows;
    }

    public static int getNumberOfRows() {
        return NUMBEROFROWS;
    }

    /**
     * Checks if a row is full or not.
     *
     * @param row row to be checked
     * @return true if row is full, false otherwise
     */
    public boolean isRowFull(final int row) {
        return rows.get(row).size() >= MAXCARDSONROW;
    }

    /**
     * Places a minion on the table.
     *
     * @param minion minion to be placed on table
     * @param row row on which the minion will be placed
     */
    public void addCardOnRow(final Minion minion, final int row) {
        rows.get(row).add(minion);
    }

    /**
     * Get a minion from the table.
     *
     * @param coords coordinates of the minion
     * @return minion at the coordinates
     */
    public Minion getCardFromTable(final Coordinates coords) {
        return rows.get(coords.getX()).get(coords.getY());
    }

    /**
     * Deletes a minion from the table.
     *
     * @param coords coordinates of the minion
     */
    public void deleteCard(final Coordinates coords) {
        rows.get(coords.getX()).remove(coords.getY());
    }

    /**
     * Checks if a minion has attacked.
     *
     * @param coords coordinates of the minion
     * @return true if the minion has attacked, false otherwise
     */
    public boolean cardHasAttacked(final Coordinates coords) {
        return rows.get(coords.getX()).get(coords.getY()).hasAttacked();
    }

    /**
     * Checks if a minion is an enemy.
     *
     * @param player current player
     * @param coords coordinates of the minion
     * @return true if the minion is an enemy, false otherwise
     */
    public boolean isEnemy(final Player player, final Coordinates coords) {
        return coords.getX() != player.getBackRow() && coords.getX() != player.getFrontRow();
    }

    /**
     * Freezes a minion.
     *
     * @param coords coordinates of the minion
     */
    public void freezeCard(final Coordinates coords) {
        rows.get(coords.getX()).get(coords.getY()).setFrozen(true);
    }

    /**
     * Resets attacks on a certain row.
     *
     * @param row row to reset attack on
     */
    public void resetAttacksOnRow(final int row) {
        for (final Minion minion : rows.get(row)) {
            minion.setHasAttacked(false);
        }
    }

    /**
     * Gets all minions currently on the table.
     *
     * @return lists of minions on each row
     */
    public ArrayList<ArrayList<Minion>> getAllCards() {
        return new ArrayList<>(rows);
    }

    /**
     * Unfreezes the minions on a certain row.
     *
     * @param row row on which to unfreeze cards
     */
    public void unfreezeCardsOnRow(final int row) {
        for (final Minion minion : rows.get(row)) {
            if (minion.isFrozen()) {
                minion.setFrozen(false);
            }
        }
    }

    /**
     * Checks if a minion is frozen or not.
     *
     * @param coords coordinates of the minion
     * @return true if the minion is frozen, false otherwise
     */
    public boolean isCardFrozen(final Coordinates coords) {
        return rows.get(coords.getX()).get(coords.getY()).isFrozen();
    }

    /**
     * Gets all frozen cards currently on the table.
     *
     * @return lists of frozen minions on each row
     */
    public ArrayList<AbstractCard> getAllFrozenCards() {
        final ArrayList<AbstractCard> minions = new ArrayList<>();
        for (int i = 0; i < NUMBEROFROWS; ++i) {
            for (int j = 0; j < rows.get(i).size(); ++j) {
                final Minion minion = rows.get(i).get(j);
                if (minion.isFrozen()) {
                    minions.add(minion);
                }
            }
        }
        return minions;
    }
}

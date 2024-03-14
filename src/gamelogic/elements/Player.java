package gamelogic.elements;

import cards.AbstractCard;
import cards.CardFactory;
import cards.Hero;
import fileio.CardInput;

import java.util.ArrayList;

public final class Player {

    private final Hero hero;

    private final ArrayList<AbstractCard> deck;

    private final ArrayList<AbstractCard> hand;

    /**
     * Index of front row on the table.
     */
    private final int frontRow;

    /**
     * Index of back row on the table.
     */
    private final int backRow;

    private int mana;

    /**
     * Number of tanks currently on table.
     */
    private int tanks = 0;

    public Player(final ArrayList<CardInput> deck, final CardInput hero,
                  final int frontRow, final int backRow) {
        final CardFactory cardFactory = new CardFactory();
        this.deck = new ArrayList<>();
        this.hero = (Hero) cardFactory.getCard(hero);
        for (final CardInput cardInput : deck) {
            this.deck.add(cardFactory.getCard(cardInput));
        }
        this.hand = new ArrayList<>();
        this.mana = 1;
        this.frontRow = frontRow;
        this.backRow = backRow;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Substracts mana from the player.
     *
     * @param value value to be substracted
     */
    public void substractMana(final int value) {
        this.mana -= value;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<AbstractCard> getDeck() {
        return deck;
    }

    public ArrayList<AbstractCard> getHand() {
        return hand;
    }

    public int getFrontRow() {
        return frontRow;
    }

    public int getBackRow() {
        return backRow;
    }

    /**
     * @return true if the player has tanks, false otherwise
     */
    public boolean hasTanks() {
        return tanks > 0;
    }

    /**
     * Adds a tank to the player.
     */
    public void addTank() {
        this.tanks++;
    }

    /**
     * Removes a tank from the player.
     */
    public void removeTank() {
        this.tanks--;
    }

    /**
     * Draws a card and removes it from the deck.
     */
    public void drawCard() {
        if (deck.size() > 0) {
            hand.add(deck.get(0));
            deck.remove(0);
        }
    }
}

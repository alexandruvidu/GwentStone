package cards;

import cards.Enums.CardsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.CardInput;

import java.util.ArrayList;


/**
 * Abstract class for cards with basic fields and functions for every type of card.
 */
public abstract class AbstractCard {
    private final CardsEnum cardType;
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean hasAttacked = false;

    protected AbstractCard(final AbstractCard card) {
        this.name = card.name;
        this.mana = card.mana;
        this.colors = card.colors;
        this.description = card.description;
        this.cardType = card.cardType;
    }

    protected AbstractCard(final CardInput input, final CardsEnum cardType) {
        this.mana = input.getMana();
        this.description = input.getDescription();
        this.colors = input.getColors();
        this.name = input.getName();
        this.cardType = cardType;
    }

    @JsonIgnore
    public final CardsEnum getCardType() {
        return cardType;
    }

    @JsonProperty("mana")
    public final int getCost() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final boolean hasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Substracts health from a card.
     *
     * @param value value to be substracted
     */
    public void takeDamage(final int value) {

    }
}

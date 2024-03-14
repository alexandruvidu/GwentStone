package cards;

import cards.Enums.CardsEnum;
import cards.Enums.MinionsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;

public class Minion extends AbstractCard {
    private final MinionsEnum minionType;
    private int attackDamage;
    private int health;
    private final int row;
    private final boolean isTank;
    private boolean isFrozen;

    public Minion(final CardInput cardInput, final boolean isTank,
                  final int row, final MinionsEnum minionType) {
        super(cardInput, CardsEnum.MINION);
        this.attackDamage = cardInput.getAttackDamage();
        this.health = cardInput.getHealth();
        this.isTank = isTank;
        this.row = row;
        this.minionType = minionType;
    }

    public Minion(final Minion minion) {
        super(minion);
        this.attackDamage = minion.getAttackDamage();
        this.health = minion.getHealth();
        this.isTank = minion.isTank();
        this.row = minion.getRow();
        this.minionType = minion.getMinionType();

    }

    @JsonIgnore
    public final MinionsEnum getMinionType() {
        return minionType;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    /**
     * {@inheritDoc}
     */
    public final void takeDamage(final int value) {
        this.health -= value;
    }

    @JsonIgnore
    public final int getRow() {
        return row;
    }

    @JsonIgnore
    public final boolean isTank() {
        return isTank;
    }

    @JsonIgnore
    public final boolean isFrozen() {
        return isFrozen;
    }

    public final void setFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    /**
     * Executes the ability of a minion.
     *
     * @param gameInstance game instance to be able to locate a minion
     * @param attackedMinionCoords row on which to use ability
     */
    public void ability(final Game gameInstance, final Coordinates attackedMinionCoords) {

    }
}

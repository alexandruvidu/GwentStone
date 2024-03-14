package cards;

import cards.Enums.CardsEnum;
import cards.Enums.HeroesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;
import gamelogic.elements.Table;

/**
 * Class that adds specific functionality for every hero.
 */
public class Hero extends AbstractCard {

    private final HeroesEnum heroType;

    private int health = 30;

    public Hero(final CardInput cardInput, final HeroesEnum heroType) {
        super(cardInput, CardsEnum.HERO);
        this.heroType = heroType;
    }

    protected Hero(final Hero hero) {
        super(hero);
        this.heroType = hero.heroType;
        this.health = hero.health;
    }

    @JsonIgnore
    public final HeroesEnum getHeroType() {
        return heroType;
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

    /**
     * Executes the ability of a hero.
     *
     * @param table game table on which to use ability
     * @param row row on which to use ability
     */
    public void ability(final Table table, final int row) {

    }
}

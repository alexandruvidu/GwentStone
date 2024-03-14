package cards;

import cards.Enums.CardsEnum;
import cards.Enums.EnvironmentsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;
import gamelogic.Game;

public class Environment extends AbstractCard {
    private final EnvironmentsEnum environmentType;

    protected Environment(final Environment card) {
        super(card);
        this.environmentType = card.getEnvironmentType();
    }

    public Environment(final CardInput cardInput, final EnvironmentsEnum environmentType) {
        super(cardInput, CardsEnum.ENVIRONMENT);
        this.environmentType = environmentType;
    }

    @JsonIgnore
    public final EnvironmentsEnum getEnvironmentType() {
        return environmentType;
    }

    /**
     * Executes the ability of an environment card.
     *
     * @param gameInstance game instance to be able to access all the needed fields
     * @param row row on which to use ability
     */
    public void ability(final Game gameInstance, final int row) {

    }
}

package cards.EnvironmentCards;

import cards.Enums.EnvironmentsEnum;
import cards.Environment;
import cards.Minion;
import fileio.CardInput;
import gamelogic.Game;

public final class Winterfell extends Environment {
    public Winterfell(final CardInput cardInput) {
        super(cardInput, EnvironmentsEnum.WINTERFELL);
    }

    public Winterfell(final Environment card) {
        super(card);
    }

    /**
     * @param gameInstance current game
     * @param row affected row
     */
    public void ability(final Game gameInstance, final int row) {
        for (final Minion minion : gameInstance.getTable().getRows().get(row)) {
            minion.setFrozen(true);
        }
    }
}

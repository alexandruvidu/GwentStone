package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;

public final class Miraj extends Minion {
    public Miraj(final CardInput cardInput) {
        super(cardInput, false, 1, MinionsEnum.MIRAJ);
    }

    public Miraj(final Minion minion) {
        super(minion);
    }

    @Override
    public void ability(final Game gameInstance, final Coordinates attackedMinionCoords) {
        final Minion attackedMinion =
                gameInstance.getTable().getCardFromTable(attackedMinionCoords);
        final int healthAttacked = attackedMinion.getHealth();
        attackedMinion.setHealth(this.getHealth());
        this.setHealth(healthAttacked);
    }
}

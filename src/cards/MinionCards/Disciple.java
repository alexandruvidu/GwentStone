package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;

public final class Disciple extends Minion {
    public Disciple(final CardInput cardInput) {
        super(cardInput, false, 0, MinionsEnum.DISCIPLE);
    }

    public Disciple(final Minion minion) {
        super(minion);
    }

    @Override
    public void ability(final Game gameInstance, final Coordinates targetedMinionCoords) {
        final Minion targetedMinion =
                gameInstance.getTable().getCardFromTable(targetedMinionCoords);
        targetedMinion.setHealth(targetedMinion.getHealth() + 2);
    }
}

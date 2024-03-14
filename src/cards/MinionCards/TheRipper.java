package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;
import gamelogic.elements.Table;

public final class TheRipper extends Minion {
    public TheRipper(final CardInput cardInput) {
        super(cardInput, false, 1, MinionsEnum.THERIPPER);
    }

    public TheRipper(final Minion minion) {
        super(minion);
    }

    @Override
    public void ability(final Game gameInstance, final Coordinates attackedMinionCoords) {
        final Table table = gameInstance.getTable();
        final Minion attackedMinion = table.getCardFromTable(attackedMinionCoords);
        attackedMinion.setAttackDamage(attackedMinion.getAttackDamage()
                                    - Math.min(attackedMinion.getAttackDamage(), 2));
    }
}

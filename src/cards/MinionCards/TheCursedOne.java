package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;

public final class TheCursedOne extends Minion {
    public TheCursedOne(final CardInput cardInput) {
        super(cardInput, false, 0, MinionsEnum.THECURSEDONE);
    }

    public TheCursedOne(final Minion minion) {
        super(minion);
    }

    @Override
    public void ability(final Game gameInstance, final Coordinates attackedMinionCoords) {
        final Minion attackedMinion =
                gameInstance.getTable().getCardFromTable(attackedMinionCoords);
        if (attackedMinion.getAttackDamage() == 0) {
            gameInstance.getTable().deleteCard(attackedMinionCoords);
            if (attackedMinion.isTank()) {
                if (gameInstance.getWhoseTurn() == gameInstance.getPlayer1()) {
                    gameInstance.getPlayer2().removeTank();
                } else {
                    gameInstance.getPlayer1().removeTank();
                }
            }
        } else {
            final int attackedHealth = attackedMinion.getHealth();
            attackedMinion.setHealth(attackedMinion.getAttackDamage());
            attackedMinion.setAttackDamage(attackedHealth);
        }
    }
}

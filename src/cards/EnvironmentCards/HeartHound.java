package cards.EnvironmentCards;

import cards.Enums.EnvironmentsEnum;
import cards.Environment;
import cards.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;

public final class HeartHound extends Environment {
    public HeartHound(final CardInput cardInput) {
        super(cardInput, EnvironmentsEnum.HEARTHOUND);
    }

    public HeartHound(final Environment card) {
        super(card);
    }

    /**
     * @param gameInstance current game
     * @param row affected row
     */
    public void ability(final Game gameInstance, final int row) {
        int posMaxHealth = -1;
        int maxHealth = -1;
        int column;
        for (column = 0; column < gameInstance.getTable().getRows().get(row).size(); ++column) {
            final int minionHealth =
                    gameInstance.getTable().getRows().get(row).get(column).getHealth();
            if (minionHealth > maxHealth) {
                maxHealth = minionHealth;
                posMaxHealth = column;
            }
        }
        final Coordinates coords = new Coordinates();
        coords.setX(row);
        coords.setY(posMaxHealth);
        final Minion stolenMinion = gameInstance.getTable().getCardFromTable(coords);
        if (stolenMinion.getRow() == 0) {
            gameInstance.getTable().addCardOnRow(stolenMinion,
                    gameInstance.getWhoseTurn().getBackRow());
        } else {
            gameInstance.getTable().addCardOnRow(stolenMinion,
                    gameInstance.getWhoseTurn().getFrontRow());
        }
    }
}

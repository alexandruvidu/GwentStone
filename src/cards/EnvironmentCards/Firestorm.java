package cards.EnvironmentCards;

import cards.Enums.EnvironmentsEnum;
import cards.Environment;
import cards.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.Game;
import gamelogic.elements.Table;

public final class Firestorm extends Environment {
    public Firestorm(final CardInput cardInput) {
        super(cardInput, EnvironmentsEnum.FIRESTORM);
    }

    public Firestorm(final Environment card) {
        super(card);
    }

    /**
     * @param gameInstance current game
     * @param row affected row
     */
    public void ability(final Game gameInstance, final int row) {
        final Table table = gameInstance.getTable();
        final Coordinates coords = new Coordinates();
        coords.setX(row);
        for (int j = 0; j < table.getRows().get(row).size(); ++j) {
            coords.setY(j);
            final Minion minion = table.getCardFromTable(coords);
            if (minion.getHealth() == 1) {
                gameInstance.getTable().deleteCard(coords);
                --j;
            } else {
                minion.setHealth(minion.getHealth() - 1);
            }
        }
    }
}

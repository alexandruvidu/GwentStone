package cards.HeroCards;

import cards.Enums.HeroesEnum;
import cards.Hero;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.elements.Table;

public final class EmpressThorina extends Hero {
    public EmpressThorina(final CardInput cardInput) {
        super(cardInput, HeroesEnum.EMPRESSTHORINA);
    }

    public EmpressThorina(final Hero card) {
        super(card);
    }

    /**
     * {@inheritDoc}
     */
    public void ability(final Table table, final int row) {
        int posMaxHealth = -1;
        int maxHealth = -1;
        int column;

        for (column = 0; column < table.getRows().get(row).size(); ++column) {
            final int minionHealth =
                    table.getRows().get(row).get(column).getHealth();
            if (minionHealth > maxHealth) {
                maxHealth = minionHealth;
                posMaxHealth = column;
            }
        }

        final Coordinates coords = new Coordinates();
        coords.setX(row);
        coords.setY(posMaxHealth);
        table.deleteCard(coords);
    }
}

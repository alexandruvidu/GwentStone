package cards.HeroCards;

import cards.Enums.HeroesEnum;
import cards.Hero;
import fileio.CardInput;
import fileio.Coordinates;
import gamelogic.elements.Table;

public final class LordRoyce extends Hero {
    public LordRoyce(final CardInput cardInput) {
        super(cardInput, HeroesEnum.LORDROYCE);
    }

    public LordRoyce(final Hero card) {
        super(card);
    }

    /**
     * {@inheritDoc}
     */
    public void ability(final Table table, final int row) {
        int posMaxAttack = -1;
        int maxAttack = -1;
        for (int column = 0; column < table.getRows().get(row).size(); ++column) {
            final int minionAttack = table.getRows().get(row).get(column).getAttackDamage();
            if (minionAttack > maxAttack) {
                maxAttack = minionAttack;
                posMaxAttack = column;
            }
        }
        final Coordinates coords = new Coordinates();
        coords.setX(row);
        coords.setY(posMaxAttack);
        table.freezeCard(coords);
    }
}

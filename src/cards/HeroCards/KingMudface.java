package cards.HeroCards;

import cards.Enums.HeroesEnum;
import cards.Hero;
import cards.Minion;
import fileio.CardInput;
import gamelogic.elements.Table;

public final class KingMudface extends Hero {
    public KingMudface(final CardInput cardInput) {
        super(cardInput, HeroesEnum.KINGMUDFACE);
    }

    public KingMudface(final Hero card) {
        super(card);
    }

    /**
     * {@inheritDoc}
     */
    public void ability(final Table table, final int row) {
        for (final Minion minion : table.getRows().get(row)) {
            minion.setHealth(minion.getHealth() + 1);
        }
    }
}

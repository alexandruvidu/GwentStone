package cards.HeroCards;

import cards.Enums.HeroesEnum;
import cards.Hero;
import cards.Minion;
import fileio.CardInput;
import gamelogic.elements.Table;

public final class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput, HeroesEnum.GENREALKOCIORAW);
    }

    public GeneralKocioraw(final Hero card) {
        super(card);
    }

    /**
     * {@inheritDoc}
     */
    public void ability(final Table table, final int row) {
        for (final Minion minion : table.getRows().get(row)) {
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }
    }
}

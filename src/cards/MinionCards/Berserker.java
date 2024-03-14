package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;

public final class Berserker extends Minion {
    public Berserker(final CardInput cardInput) {
        super(cardInput, false, 0, MinionsEnum.BERSERKER);
    }

    public Berserker(final Minion minion) {
        super(minion);
    }
}

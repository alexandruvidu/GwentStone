package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;

public final class Goliath extends Minion {
    public Goliath(final CardInput cardInput) {
        super(cardInput, true, 1, MinionsEnum.GOLIATH);
    }

    public Goliath(final Minion minion) {
        super(minion);
    }
}

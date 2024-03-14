package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;

public final class Sentinel extends Minion {
    public Sentinel(final CardInput cardInput) {
        super(cardInput, false, 0, MinionsEnum.SENTINEL);
    }

    public Sentinel(final Minion minion) {
        super(minion);
    }
}

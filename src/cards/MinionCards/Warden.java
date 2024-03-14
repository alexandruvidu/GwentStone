package cards.MinionCards;

import cards.Enums.MinionsEnum;
import cards.Minion;
import fileio.CardInput;

public final class Warden extends Minion {
    public Warden(final CardInput cardInput) {
        super(cardInput, true, 1, MinionsEnum.WARDEN);
    }

    public Warden(final Minion minion) {
        super(minion);
    }
}

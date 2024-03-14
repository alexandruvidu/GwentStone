package cards;

import cards.Enums.CardsEnum;
import cards.Enums.EnvironmentsEnum;
import cards.Enums.HeroesEnum;
import cards.Enums.MinionsEnum;
import cards.EnvironmentCards.Firestorm;
import cards.EnvironmentCards.HeartHound;
import cards.EnvironmentCards.Winterfell;
import cards.HeroCards.EmpressThorina;
import cards.HeroCards.GeneralKocioraw;
import cards.HeroCards.KingMudface;
import cards.HeroCards.LordRoyce;
import cards.MinionCards.Berserker;
import cards.MinionCards.Disciple;
import cards.MinionCards.Goliath;
import cards.MinionCards.Miraj;
import cards.MinionCards.Sentinel;
import cards.MinionCards.TheCursedOne;
import cards.MinionCards.TheRipper;
import cards.MinionCards.Warden;
import fileio.CardInput;

/**
 * Factory for all types of cards.
 */
public final class CardFactory {
    /**
     * Takes data of a card from input and returns the corresponding card.
     *
     * @param card card to be returned
     *
     * @return card based on the type of parameter
     */
    public AbstractCard getCard(final CardInput card) {
        if (card.getName().equals(MinionsEnum.SENTINEL.getName())) {
            return new Sentinel(card);
        } else if (card.getName().equals(MinionsEnum.BERSERKER.getName())) {
            return new Berserker(card);
        } else if (card.getName().equals(MinionsEnum.GOLIATH.getName())) {
            return new Goliath(card);
        } else if (card.getName().equals(MinionsEnum.WARDEN.getName())) {
            return new Warden(card);
        } else if (card.getName().equals(MinionsEnum.MIRAJ.getName())) {
            return new Miraj(card);
        } else if (card.getName().equals(MinionsEnum.THERIPPER.getName())) {
            return new TheRipper(card);
        } else if (card.getName().equals(MinionsEnum.DISCIPLE.getName())) {
            return new Disciple(card);
        } else if (card.getName().equals(MinionsEnum.THECURSEDONE.getName())) {
            return new TheCursedOne(card);
        } else if (card.getName().equals(EnvironmentsEnum.FIRESTORM.getName())) {
            return new Firestorm(card);
        } else if (card.getName().equals(EnvironmentsEnum.HEARTHOUND.getName())) {
            return new HeartHound(card);
        } else if (card.getName().equals(EnvironmentsEnum.WINTERFELL.getName())) {
            return new Winterfell(card);
        } else if (card.getName().equals(HeroesEnum.EMPRESSTHORINA.getName())) {
            return new EmpressThorina(card);
        } else if (card.getName().equals(HeroesEnum.KINGMUDFACE.getName())) {
            return new KingMudface(card);
        } else if (card.getName().equals(HeroesEnum.LORDROYCE.getName())) {
            return new LordRoyce(card);
        } else if (card.getName().equals(HeroesEnum.GENREALKOCIORAW.getName())) {
            return new GeneralKocioraw(card);
        }
        return null;
    }

    /**
     * Card duplication method for deep-copy.
     *
     * @param card card to be copied
     *
     * @return duplicate of the card
     */
    public AbstractCard duplicateCard(final AbstractCard card) {
        if (card.getCardType() == CardsEnum.MINION) {
            final Minion minion = (Minion) card;
            if (minion.getMinionType().equals(MinionsEnum.SENTINEL)) {
                return new Sentinel(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.BERSERKER)) {
                return new Berserker(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.GOLIATH)) {
                return new Goliath(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.WARDEN)) {
                return new Warden(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.MIRAJ)) {
                return new Miraj(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.THERIPPER)) {
                return new TheRipper(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.DISCIPLE)) {
                return new Disciple(minion);
            } else if (minion.getMinionType().equals(MinionsEnum.THECURSEDONE)) {
                return new TheCursedOne(minion);
            }
        } else if (card.getCardType() == CardsEnum.ENVIRONMENT) {
            final Environment environment = (Environment) card;
            if (environment.getName().equals(EnvironmentsEnum.FIRESTORM.getName())) {
                return new Firestorm(environment);
            } else if (environment.getName().equals(EnvironmentsEnum.HEARTHOUND.getName())) {
                return new HeartHound(environment);
            } else if (environment.getName().equals(EnvironmentsEnum.WINTERFELL.getName())) {
                return new Winterfell(environment);
            }
        } else {
            final Hero hero = (Hero) card;
            if (hero.getName().equals(HeroesEnum.EMPRESSTHORINA.getName())) {
                return new EmpressThorina(hero);
            } else if (hero.getName().equals(HeroesEnum.KINGMUDFACE.getName())) {
                return new KingMudface(hero);
            } else if (hero.getName().equals(HeroesEnum.LORDROYCE.getName())) {
                return new LordRoyce(hero);
            } else if (hero.getName().equals(HeroesEnum.GENREALKOCIORAW.getName())) {
                return new GeneralKocioraw(hero);
            }
        }
        return null;
    }
}

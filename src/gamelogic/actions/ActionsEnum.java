package gamelogic.actions;

public enum ActionsEnum {
    // actions
    ENDPLAYERTURN("endPlayerTurn"),
    PLACECARD("placeCard"),
    CARDUSESATTACK("cardUsesAttack"),
    CARDUSESABILITY("cardUsesAbility"),
    USEATTACKHERO("useAttackHero"),
    USEHEROABILITY("useHeroAbility"),
    USEENVIRONMENTCARD("useEnvironmentCard"),

    // debug actions
    GETCARDSINHAND("getCardsInHand"),
    GETPLAYERDECK("getPlayerDeck"),
    GETCARDSONTABLE("getCardsOnTable"),
    GETPLAYERTURN("getPlayerTurn"),
    GETPLAYERHERO("getPlayerHero"),
    GETCARDATPOSITION("getCardAtPosition"),
    GETPLAYERMANA("getPlayerMana"),
    GETENVIRONMENTCARDSINHAND("getEnvironmentCardsInHand"),
    GETFROZENCARDSONTABLE("getFrozenCardsOnTable"),
    // statistics
    GETTOTALGAMESPLAYED("getTotalGamesPlayed"),
    GETPLAYERONEWINS("getPlayerOneWins"),
    GETPLAYERTWOWINS("getPlayerTwoWins"),
    GAMEENDED("gameEnded");

    private final String name;

    ActionsEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

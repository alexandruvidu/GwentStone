package cards.Enums;

public enum MinionsEnum {
    SENTINEL("Sentinel"),
    BERSERKER("Berserker"),
    GOLIATH("Goliath"),
    WARDEN("Warden"),
    MIRAJ("Miraj"),
    THERIPPER("The Ripper"),
    DISCIPLE("Disciple"),
    THECURSEDONE("The Cursed One");

    private final String name;

    MinionsEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

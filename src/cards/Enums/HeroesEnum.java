package cards.Enums;

public enum HeroesEnum {
    LORDROYCE("Lord Royce"),
    EMPRESSTHORINA("Empress Thorina"),
    KINGMUDFACE("King Mudface"),
    GENREALKOCIORAW("General Kocioraw");

    private final String name;

    HeroesEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

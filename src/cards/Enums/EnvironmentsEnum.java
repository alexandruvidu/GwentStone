package cards.Enums;

public enum EnvironmentsEnum {
    FIRESTORM("Firestorm"),
    WINTERFELL("Winterfell"),
    HEARTHOUND("Heart Hound");

    private final String name;

    EnvironmentsEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

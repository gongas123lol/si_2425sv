package isel.sisinf.model;

public enum CardType {
    RESIDENT("resident"),
    TOURIST("tourist");

    private final String dbValue;

    CardType(String dbValue) { this.dbValue = dbValue; }

    public String getDbValue() { return dbValue; }

    public static CardType fromDb(String value) {
        for (CardType ct : values())
            if (ct.dbValue.equalsIgnoreCase(value))
                return ct;
        throw new IllegalArgumentException("Unknown CardType: " + value);
    }
}
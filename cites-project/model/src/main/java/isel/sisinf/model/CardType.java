package isel.sisinf.model;

public enum CardType {
    RESIDENT("resident"),
    TOURIST("tourist");

    private final String dbValue;

    CardType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static CardType fromDb(String value) {
        if (value == null) return null;

        String cleanValue = value.trim().toLowerCase();
        System.out.println("Cleaned DB value: '" + cleanValue + "'");

        for (CardType ct : values()) {
            System.out.println("Comparing to: '" + ct.dbValue + "'");
            if (ct.dbValue.equalsIgnoreCase(cleanValue)) {
                return ct;
            }
        }

        throw new IllegalArgumentException("Unknown CardType: [" + value + "]");
    }
}

package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public enum ShotType {
    BITE("0"), HIT("1"), SINGLE("2"), AIMED("3"), SHOT3("4"), SHOT4("5"), SHOT5("6"),
    THROW("7"), USE("8"), LAUNCH("10"), ENERGY_SHORT("11"), ENERGY_LONG("12"),
    OFFLINE("99");
    private String code;

    ShotType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ShotType getShotTypeByCode(String code) {
        for (ShotType shotType : values()) {
            if (shotType.getCode().equals(code)) {
                return shotType;
            }
        }
        throw new IllegalStateException("Unknown shot type: " + code);
    }
}
package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public enum LocationDirection {
    NONE("5"),

    N("2"),
    S("8"),
    W("4"),
    E("6"),
    
    SW("7"),
    SE("9"),

    NW("1"),
    NE("3");

    private String code;

    LocationDirection(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static LocationDirection getLocationByCode(String code) {
        for (LocationDirection direction : values()) {
            if (direction.getCode().equals(code)) {
                return direction;
            }
        }
        throw new IllegalStateException("Unknown LocationCode: " + code);
    }
}

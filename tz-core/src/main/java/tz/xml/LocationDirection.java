package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public enum LocationDirection {
    NONE("5", 0, 0),
    OUTSIDE("0", 0, 0),

    N("2", 0, -1),
    S("8", 0 , 1),
    W("4", -1, 0),
    E("6", 1, 0),
    
    SW("7", -1, 1),
    SE("9", 1, 1),

    NW("1", -1 , -1),
    NE("3", 1, -1);

    private String code;
    private int dx;
    private int dy;

    LocationDirection(String code, int dx, int dy) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
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

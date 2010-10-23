package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public class ShotDefinition {
    private ShotType type;

    private int od;

    public ShotDefinition(ShotType type, int od) {
        this.type = type;
        this.od = od;
    }

    public ShotType getType() {
        return type;
    }

    public int getOd() {
        return od;
    }

    @Override
    public String toString() {
        return type.getCode() + "-" +  od;
    }

    public static ShotDefinition fromString(String s) {
        int index = s.indexOf('-');
        ShotType type = ShotType.getShotTypeByCode(s.substring(0, index));
        int od = Integer.parseInt(s.substring(index + 1));
        return new ShotDefinition(type ,od);
    }
}

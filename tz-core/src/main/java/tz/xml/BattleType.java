package tz.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public enum BattleType {
    MINE("D"), GRASS("A"), SAND("B"), CLAY("C"), BUILDING("E"), SNOW("H");

    private static Map<String, BattleType> map = new HashMap<String, BattleType>();

    private String code;

    static {
        for (BattleType type : values()) {
            map.put(type.code, type);
        }
    }

    BattleType(String code) {
        this.code = code;
    }

    public static BattleType getBattleTypeByCode(String code) {
        BattleType type = map.get(code);
        if (type == null) {
            throw new IllegalStateException("unknown battle type: " + code);
        }
        return type;
    }
}

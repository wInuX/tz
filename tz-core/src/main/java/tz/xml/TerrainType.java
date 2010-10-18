package tz.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public enum TerrainType {
    MINE("D"), GRASS("A"), SAND("B"), CLAY("C"), BUILDING("E"), SNOW("H");

    private static Map<String, TerrainType> map = new HashMap<String, TerrainType>();

    private String code;

    static {
        for (TerrainType type : values()) {
            map.put(type.code, type);
        }
    }

    TerrainType(String code) {
        this.code = code;
    }

    public static TerrainType getBattleTypeByCode(String code) {
        TerrainType type = map.get(code);
        if (type == null) {
            throw new IllegalStateException("unknown battle type: " + code);
        }
        return type;
    }
}

package tz.xml;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public enum TerrainType {
    MINE("D"), GRASS("A"), SAND("B"), CLAY("C"), BUILDING("E"), SNOW("H"), UNKNOWN("?");
    public static final Logger LOG = Logger.getLogger(TerrainType.class);
    
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
            LOG.warn("unknown terrain type: " + code);
            return UNKNOWN;
        }
        return type;
    }
}

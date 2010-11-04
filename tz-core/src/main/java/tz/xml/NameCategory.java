package tz.xml;

import org.apache.log4j.Logger;


/**
 * @author Dmitry Shyshkin
 */
public enum NameCategory {
    AMMO("a"),
    BOOT("b"),
    VEST("c"),
    MEDICINE("d"),
    ENERGY("e"),
    THROWING("g"),
    HELM("h"),
    ENERGY_AMMO("i"),
    ITEM("j"),
    MELEE("k"),
    ARMGUARD("l"),
    LIGHT_WEAPON("p"),
    RESOURCE("s"),
    TROUSERS("t"),
    MODIFICATION("u"),
    RIFLE("v"),
    HEAVY_WEAPON("w"),
    DOCUMENT("z"),
    UNKNOWN("?");

    private static final Logger LOG = Logger.getLogger(NameCategory.class);
    private String code;

    NameCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static NameCategory getByCode(String code) {
        for (NameCategory category : values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }
        return UNKNOWN;
    }
}

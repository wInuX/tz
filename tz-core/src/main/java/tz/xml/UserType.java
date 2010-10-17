package tz.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public enum UserType {
    PLAYER(null),
    RAT("rat"),
    STICH("stich"),
    SPIDER("spd"),
    STING("vzz"),
    ANT("ant"),
    DOG("dog"),
    ERGO("erg"),
    MAN("man"),
    ALG("alg"), ALS("als"), ALB("alb"),
    RANGER("rng"),
    CRASHER("crs"),
    ROBOT("rbt"),
    ZOMBIE("zmb"),
    BOGOMOL("mts"),
    WORM("wrm"),
    TIGER("tgr"),
    TURRET("turret"),
    BMINE("bmine"),
    STD("std"),
    SCORPION("scr"),
    BALL("bll"),
    PSI_JAMMER("pjm"),
    LABORANT("col"),
    RADAR("rdr"),
    HOSTAGE("hst");

    private static Map<String, UserType> map = new HashMap<String, UserType>();
    private String code;

    static {
        for (UserType type: values()) {
            map.put(type.code, type);
        }
    }

    UserType(String code) {
        this.code = code;
    }

    public static UserType getUserTypeByLogin(String login) {
        if (login.startsWith("$")) {
            String code = login.replaceAll("^\\$(.*?)\\d+$", "$1");
            UserType type = map.get(code);
            if (type == null) {
                throw new IllegalStateException("Unknown bot type: " + code);
            }
            return type;
        } else {
            return PLAYER;
        }
    }
}

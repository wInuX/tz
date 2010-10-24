package tz.xml;

import org.apache.log4j.Logger;

/**
 * @author Dmitry Shyshkin
 */
public enum ActionMode {
    TURN("1"),
    MOVE("2"),
    RELOAD("3"),
    UNKNOWN_4("4"),
    FIRE("5"),
    POSITION("6"),
    DIED("7"),
    GET_ITEM("8"),
    STATE_CHANGE("12"),
    TAKE_ON("16"),
    KILLED("20"),

    UNKNOWN("?");
    public static final Logger LOG = Logger.getLogger(ActionMode.class);

    private String code;

    ActionMode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ActionMode getActionModeByCode(String code) {
        for (ActionMode actionMode : values()) {
            if (actionMode.getCode().equals(code)) {
                return actionMode;
            }
        }
        LOG.warn("Unknown action code: " + code);
        return UNKNOWN;
    }
}

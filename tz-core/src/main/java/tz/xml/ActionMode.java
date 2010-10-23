package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public enum ActionMode {
    TURN("1"),
    MOVE("2"),
    RELOAD("3"),
    UNKNOWN1("4"),
    FIRE("5"),
    POSITION("6"),
    DIED("7"),
    GET_ITEM("8"),
    STATE_CHANGE("12"),
    KILLED("20");

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
        throw new IllegalStateException("Unknown action mode:" + code);
    }
}

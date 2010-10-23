package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public enum Slot {
    FEET("B"), LEGS("A"), BREST("C"), HEAD("F"), LEFT_ARM("D"), RIGHT_ARM("E"), LEFT_HAND("G"), RIGHT_HAND("H");
    private String code;

    Slot(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Slot getSlotByCode(String code) {
        for (Slot slot: Slot.values()) {
            if (slot.getCode().equals(code)) {
                return slot;
            }
        }
        throw new IllegalStateException("unknown slot:" + code);
    }
}

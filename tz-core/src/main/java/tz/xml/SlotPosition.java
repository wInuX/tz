package tz.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class SlotPosition {
    private List<Slot> slots;

    public SlotPosition(List<Slot> slots) {
        this.slots = slots;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Slot slot: slots) {
            sb.append(slot.getCode());
        }
        return sb.toString();
    }

    public static SlotPosition fromString(String code) {
        List<Slot> slots = new ArrayList<Slot>();
        for (char ch : code.toCharArray()) {
            slots.add(Slot.getSlotByCode(String.valueOf(ch)));
        }
        return new SlotPosition(slots);
    }
}

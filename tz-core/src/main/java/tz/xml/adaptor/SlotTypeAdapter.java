package tz.xml.adaptor;

import tz.xml.SlotPosition;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class SlotTypeAdapter extends XmlAdapter<String, SlotPosition> {
    @Override
    public SlotPosition unmarshal(String s) throws Exception {
        return s != null ? SlotPosition.fromString(s) : null;
    }

    @Override
    public String marshal(SlotPosition slotPosition) throws Exception {
        return slotPosition != null ? slotPosition.toString() : null;
    }
}

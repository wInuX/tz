package tz.xml.adaptor;

import tz.xml.SlotPosition;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
public class SlotSetTypeAdapter extends XmlAdapter<String, Set<SlotPosition>> {
    @Override
    public Set<SlotPosition> unmarshal(String s) throws Exception {
        if (s == null) {
            return null;
        }
        Set<SlotPosition> set = new LinkedHashSet<SlotPosition>();
        for (String v : s.split(",")) {
            set.add(SlotPosition.fromString(v));
        }
        return set;
    }

    @Override
    public String marshal(Set<SlotPosition> set) throws Exception {
        if (set == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(SlotPosition slot : set) {
            sb.append(",").append(slot.toString());
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }
}
package tz.xml.adaptor;

import tz.xml.ShotDefinition;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
public class ShotDefinitionSetAdapter extends XmlAdapter<String, Set<ShotDefinition>> {
    @Override
    public Set<ShotDefinition> unmarshal(String s) throws Exception {
        if (s == null) {
            return null;
        }
        Set<ShotDefinition> set = new LinkedHashSet<ShotDefinition>();
        for (String v : s.split(",")) {
            set.add(ShotDefinition.fromString(v));
        }
        return set;
    }

    @Override
    public String marshal(Set<ShotDefinition> set) throws Exception {
        if (set == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (ShotDefinition shotDefinition : set) {
            sb.append(",").append(shotDefinition.toString());
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }
}

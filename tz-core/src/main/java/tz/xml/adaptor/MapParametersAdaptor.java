package tz.xml.adaptor;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class MapParametersAdaptor extends XmlAdapter<String, List<Integer>> {
    @Override
    public List<Integer> unmarshal(String s) throws Exception {
        if (s == null) {
            return null;
        }
        List<Integer> r = new ArrayList<Integer>();
        for (String value : s.split(",")) {
            value = value.trim();
            r.add(Integer.parseInt(value));
        }
        return r;
    }

    @Override
    public String marshal(List<Integer> list) throws Exception {
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer v = iterator.next();
            sb.append(v);
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}

package tz.xml.adaptor;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class LocationAdaptor extends XmlAdapter<String, Integer> {
    @Override
    public Integer unmarshal(String s) throws Exception {
        if (s == null) {
            return null;
        }
        int v = Integer.parseInt(s);
        return v > 180 ? v - 360 : v;
    }

    @Override
    public String marshal(Integer v) throws Exception {
        if (v == null) {
            return null;
        }
        return String.valueOf(v < 0 ? v + 360 : v);
    }
}

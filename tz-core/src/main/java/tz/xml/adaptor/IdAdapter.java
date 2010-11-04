package tz.xml.adaptor;

import tz.xml.Id;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class IdAdapter extends XmlAdapter<String, Id> {
    @Override
    public Id unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return new Id(v);
    }

    @Override
    public String marshal(Id v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString();
    }
}

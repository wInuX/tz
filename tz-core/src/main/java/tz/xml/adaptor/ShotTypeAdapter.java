package tz.xml.adaptor;

import tz.xml.ShotType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class ShotTypeAdapter extends XmlAdapter<String, ShotType> {
    @Override
    public ShotType unmarshal(String s) throws Exception {
        return s != null ? ShotType.getShotTypeByCode(s) : null;
    }

    @Override
    public String marshal(ShotType shotType) throws Exception {
        return shotType != null ? shotType.getCode() : null;
    }
}

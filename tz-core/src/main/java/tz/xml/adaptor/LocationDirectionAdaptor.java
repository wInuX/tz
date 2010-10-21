package tz.xml.adaptor;

import tz.xml.LocationDirection;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class LocationDirectionAdaptor extends XmlAdapter<String, LocationDirection> {
    @Override
    public LocationDirection unmarshal(String v) throws Exception {
        return v != null ? LocationDirection.getLocationByCode(v) : null;
    }

    @Override
    public String marshal(LocationDirection v) throws Exception {
        return v != null ? v.getCode() : null;
    }
}

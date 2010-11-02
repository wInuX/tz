package tz.xml.adaptor;

import tz.xml.BuildingType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class BuildingTypeAdapter extends XmlAdapter<String, BuildingType> {
    @Override
    public BuildingType unmarshal(String s) throws Exception {
        if (s == null) {
            return null;
        }
        return new BuildingType(s);
    }

    @Override
    public String marshal(BuildingType buildingType) throws Exception {
        return buildingType != null ? buildingType.getCode() : null;
    }
}

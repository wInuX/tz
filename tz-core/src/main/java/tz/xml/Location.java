package tz.xml;

import tz.xml.adaptor.LocationAdaptor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Dmitry Shyshkin
 */
public class Location {
    @XmlAttribute(name = "X")
    @XmlJavaTypeAdapter(LocationAdaptor.class)
    private Integer x;

    @XmlAttribute(name = "Y")
    @XmlJavaTypeAdapter(LocationAdaptor.class)
    private Integer y;

    @XmlAttribute
    private String content;

    @XmlAttribute
    private TerrainType type;

    @XmlAttribute
    private int danger;
}

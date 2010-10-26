package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Shop {
    @XmlAttribute(name = "full")
    private String full;

    @XmlAttribute(name = "res")
    private String resources;

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "sale")
    private String saleId;

    @XmlAttribute(name = "count")
    private Integer count;

    @XmlAttribute(name = "askprice")
    private Integer askPrice;

    
}

package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "SH")
public class Shop {
    @XmlAttribute(name = "full")
    private String full;

    @XmlAttribute(name = "res")
    private String resources;

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "sale")
    private Id saleId;

    @XmlAttribute(name = "count")
    private Integer count;

    @XmlAttribute(name = "askprice")
    private Integer askPrice;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Integer askPrice) {
        this.askPrice = askPrice;
    }

    public Id getSaleId() {
        return saleId;
    }

    public void setSaleId(Id saleId) {
        this.saleId = saleId;
    }
}

package tz.xml;

import tz.xml.transform.XmlComposite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "N")
public class Nop {
    @XmlComposite
    private IdRange idRange;

    public IdRange getIdRange() {
        return idRange;
    }

    public void setIdRange(IdRange idRange) {
        this.idRange = idRange;
    }
}

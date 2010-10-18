package tz.xml;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "MESSAGE")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {
    @XmlElement(name = "MYPARAM")
    private MyParameters myParameters;

    @XmlElement(name = "GOLOC")
    private GoLocation goLocation;

    @XmlElement(name = "N")
    private Idle idle;

    @XmlElement(name = "MMP")
    private Map map;

    private String direct;

    public Message() {
    }

    public Message(String direct) {
        this.direct = direct;
    }

    public void accept(MessageVisitor visitor) {
        if (myParameters != null) {
            visitor.visitMyParameters(myParameters);
        }
        if (goLocation != null) {
            visitor.visitGoLocation(goLocation);
        }
    }

    public String getDirect() {
        return direct;
    }

    public Object getValue() {
        if (myParameters != null) {
            return myParameters;
        }
        if (goLocation != null) {
            return goLocation;
        }
        if (direct != null) {
            return direct;
        }
        return null;
    }
}

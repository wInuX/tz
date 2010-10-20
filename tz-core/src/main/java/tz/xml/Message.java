package tz.xml;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;

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
    private MiniMap map;

    @XmlElement(name = "L")
    private Login login;

    @XmlElement(name = "LIST")
    private ListLogin listLogin;

    @XmlElement(name = "KEY")
    private Key key;

    @XmlElement(name = "OK")
    private LoginOk loginOk;

    @XmlElement(name = "POST")
    private Post post;

    @XmlElement(name = "MMP")
    private MiniMap miniMap;

    @XmlElement(name = "AR")
    private Search search;
    
    @XmlElement(name = "GOBLD")
    private GoBuilding goBuilding;

    private String direct;

    public Message() {
    }

    public Message(Object value) {
        setValue(value);
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
        for (Field field : getClass().getDeclaredFields()) {
            Object value;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public void setValue(Object value) {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                field.set(this, field.getType() == value.getClass() ? value : null);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

package tz.xml;


import javax.xml.bind.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

    @XmlElement(name = "#noname")
    private String direct;

    @XmlElement(name = "BSTART")
    private BattleStart battleStart;

    @XmlElementRef
    private BattleAction battleAction;

    @XmlElement(name = "BEND")
    private BattleEnd battleEnd;

    @XmlElement(name = "BATTLE")
    private Battle battle;

    @XmlElement(name = "TURN")
    private Turn turn;

    @XmlElement(name = "TAKE_ON")
    private TakeOn takeOn;

    @XmlElement(name = "TAKE_OFF")
    private TakeOff takeOff;

    @XmlElement(name = "JOIN")
    private Join join;

    @XmlElement(name = "SPLIT")
    private Split split;

    @XmlElement(name = "NOP")
    private Nop nop;

    @XmlElement(name = "NEWID")
    private NewId newId;

    @XmlElement(name = "KICKASS")
    private Kickass kickass;

    @XmlElement(name = "ALERT")
    private Alert alert;

    @XmlElement(name = "DROP")
    private Drop drop;

    @XmlElement(name = "ADD_ONE")
    private AddOne addOne;

    @XmlElement(name = "CHAT_MSG")
    private ChatMessage chatMessage;

    @XmlElement(name = "SH")
    private Shop shop;

    @XmlElement(name = "HD")
    private Mine mine;

    @XmlElement(name = "ERRGO")
    private GoError goError;

    @XmlElement(name = "ERR")
    private Error error;

    public Message() {
    }

    public Message(Object value) {
        setValue(value);
    }

    public String getDirect() {
        return direct;
    }

    public Object getValue() {
        for (Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
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
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            try {
                field.set(this, field.getType().isAssignableFrom(value.getClass()) ? value : null);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}

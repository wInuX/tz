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


}

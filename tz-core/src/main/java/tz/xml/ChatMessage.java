package tz.xml;

import javax.xml.bind.annotation.*;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "CHAT_MSG")
public class ChatMessage {
    @XmlAttribute(name = "from")
    private String from;

    @XmlAttribute(name = "text")
    private String text;

    @XmlAttribute(name = "html")
    private Integer html;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getHtml() {
        return html;
    }

    public void setHtml(Integer html) {
        this.html = html;
    }
}

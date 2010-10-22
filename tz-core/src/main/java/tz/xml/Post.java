package tz.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Post {
    @XmlAttribute(name = "t")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPrivate() {
        return text.trim().startsWith("private");
    }

    public String getLogin() {
        return text.trim().replaceAll("^.*?\\[([^\\]]+)\\].*?$", "$1");
    }

    public String getMessage() {
        return text.trim().replaceAll("^.*?\\[[^\\]]+\\](.*?)$", "$1").trim();
    }
}

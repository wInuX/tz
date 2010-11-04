package tz.xml.transform.def;

import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class Context {
    private String type;

    private Map<Class<?>, ElementDef> elements;

    public Context(String type, Map<Class<?>, ElementDef> elements) {
        this.type = type;
        this.elements = elements;
    }

    public String getType() {
        return type;
    }

    public ElementDef getElementDef(Class<?> type) {
        return elements.get(type);
    }
}

package tz.xml.transform.def;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class Context {
    private String type;
    private JAXMContext jaxmContext;


    public Context(String type, JAXMContext jaxmContext) {
        this.type = type;
        this.jaxmContext = jaxmContext;
    }

    public String getType() {
        return type;
    }

    public ElementDef getElementDef(Class<?> type) {
        return jaxmContext.getElements().get(type);
    }

    public Map<Type, XmlAdapter> getAdapters() {
        return jaxmContext.getAdapters();
    }
}

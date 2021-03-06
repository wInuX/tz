package tz.xml.transform.def;

import org.apache.log4j.Logger;
import tz.service.Parser;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.lang.reflect.Type;

/**
 * @author Dmitry Shyshkin
 */
public class AttributeDef {
    public static final Logger LOG = Logger.getLogger(Parser.class);
    private String name;

    private Type type;

    private XmlAdapter adapter;

    public AttributeDef(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XmlAdapter getAdapter() {
        return adapter;
    }

    public Type getType() {
        return type;
    }

    public void setAdapter(XmlAdapter adapter) {
        this.adapter = adapter;
    }

    public Object fromString(String value, Context context) {
        if (adapter != null) {
            try {
                return adapter.unmarshal(value);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }
        if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        }
        if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        }
        if (type == String.class) {
            return value;
        }
        if (type instanceof Class) {
            Class<?> classType = (Class<?>) type;
            if (classType.isEnum()) {
                for (Object object : classType.getEnumConstants()) {
                    Enum e = (Enum) object;
                    XmlEnumValue xmlEnumValue;
                    try {
                        xmlEnumValue = e.getDeclaringClass().getField(e.name()).getAnnotation(XmlEnumValue.class);
                    } catch (NoSuchFieldException e1) {
                        throw new IllegalStateException(e1);
                    }
                    String name = xmlEnumValue != null ? xmlEnumValue.value() : e.name();
                    if (name.equals(value)) {
                        return e;
                    }
                }
                LOG.warn("No enum matches value " + value + " Enum class: " + type);
                return null;
            }
        }
        if (context.getAdapters().containsKey(type)) {
            XmlAdapter adapter = context.getAdapters().get(type);
            try {
                return adapter.unmarshal(value);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalStateException("No convertion from String to " + type);
    }

    public String toString(Object value, Context context) {
        if (adapter != null) {
            try {
                return (String) adapter.marshal(value);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        if (type == int.class || type == Integer.class) {
            return value.toString();
        }
        if (type == long.class || type == Long.class) {
            return value.toString();
        }
        if (type == double.class || type == Double.class) {
            return value.toString();
        }
        if (type == String.class) {
            return (String) value;
        }
        if (type instanceof Class) {
            Class<?> classType = (Class<?>) type;
            if (classType.isEnum()) {
                Enum e = (Enum) value;
                XmlEnumValue xmlEnumValue;
                try {
                    xmlEnumValue = e.getDeclaringClass().getField(e.name()).getAnnotation(XmlEnumValue.class);
                } catch (NoSuchFieldException e1) {
                    throw new IllegalStateException(e1);
                }
                return xmlEnumValue != null ? xmlEnumValue.value() : e.name();
            }
        }
        if (context.getAdapters().containsKey(type)) {
            XmlAdapter adapter = context.getAdapters().get(type);
            try {
                return (String) adapter.marshal(value);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalStateException("No convertion from " + type + " to string");
    }
}

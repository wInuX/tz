package tz.xml.transform.def;

import tz.Reflector;
import tz.xml.transform.ClientOnly;
import tz.xml.transform.ServerOnly;
import tz.xml.transform.XmlComposite;
import tz.xml.transform.XmlPropertyMapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class ElementDefinitionFactory {
    private List<ElementDef> definitions = new ArrayList<ElementDef>();

    public static ElementDefinitionFactory createFactory() {
        ElementDefinitionFactory factory = new ElementDefinitionFactory();
        return factory;
    }

    public Configuration register(Class<?> type) {
        ElementDef elementDef = createElementDef(type);
        definitions.add(elementDef);
        return new Configuration(new Descriptor<ElementDef>(elementDef, new Field[0], null));
    }

    public ElementDef getByName(String name, Context context) {
        for (ElementDef def : definitions) {
            if (def.getName().equals(name)) {
                return def;
            }
        }
        throw new IllegalStateException("No def for " + name + " with context " + context);
    }

    public ElementDef getByClass(Class<?> type, Context context) {
        for (ElementDef def : definitions) {
            if (def.getType() == type) {
                return def;
            }
        }
        throw new IllegalStateException("No def for type " + type + " with context " + context);
    }

    private ElementDef createElementDef(Class<?> type, Field...fields) {
        if (type == null) {
            throw new IllegalStateException();
        }
        ElementDef def = new ElementDef(type);
        XmlRootElement rootElement = type.getAnnotation(XmlRootElement.class);
        if (rootElement != null) {
            if (rootElement.name().equals("##default")) {
                throw new IllegalStateException("@XmlRootElement.name() should be specfied for " + type);
            }
            def.setName(rootElement.name());
        }
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                XmlComposite composite = field.getAnnotation(XmlComposite.class);
                if (composite != null) {
                    Field[] compositeFields = new Field[fields.length + 1];
                    System.arraycopy(fields, 0, compositeFields, 0, fields.length);
                    compositeFields[fields.length] = field;
                    ElementDef compositeDef = createElementDef(field.getType(), compositeFields);
                    Map<String, String> override = new HashMap<String, String>();
                    for (XmlPropertyMapping mapping : composite.override()) {
                        override.put(mapping.propety(), mapping.name());
                    }
                    for (Descriptor<AttributeDef> descriptor : compositeDef.getAttributes()) {
                        String name = descriptor.getDescription().getName();
                        if (override.containsKey(name)) {
                            descriptor.getDescription().setName(override.get(name));
                        }
                        def.getAttributes().add(descriptor);
                    }
                    def.getElements().addAll(compositeDef.getElements());
                    continue;
                }
                XmlAttribute attribute = field.getAnnotation(XmlAttribute.class);
                if (attribute != null) {
                    String name = attribute.name().equals("##default") ? field.getName() : attribute.name();
                    AttributeDef attributeDef = new AttributeDef(field.getType(), name);
                    XmlJavaTypeAdapter adapter = field.getAnnotation(XmlJavaTypeAdapter.class);
                    if (adapter != null) {
                        attributeDef.setAdapter(Reflector.newInstance(adapter.value()));
                    }
                    Descriptor<AttributeDef> descriptor = new Descriptor<AttributeDef>(attributeDef, fields, field);
                    if (field.getAnnotation(ClientOnly.class) != null) {
                        descriptor.setContextType("client");
                    }
                    if (field.getAnnotation(ServerOnly.class) != null) {
                        descriptor.setContextType("server");
                    }
                    def.getAttributes().add(descriptor);
                }
                XmlElement element = field.getAnnotation(XmlElement.class);
                if (element != null) {
                    if (field.getType() != List.class) {
                        ElementDef elementDef = createElementDef(field.getType());
                        String name = element.name().equals("##default") ? field.getName() : element.name();
                        elementDef.setName(name);
                        def.getElements().add(new Descriptor<ElementDef>(elementDef, fields, field));
                    } else {
                        Class<?> itemClass = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                        ElementDef elementDef = createElementDef(itemClass);
                        String name = element.name().equals("##default") ? field.getName() : element.name();
                        elementDef.setName(name);
                        Descriptor<ElementDef> descriptor = new Descriptor<ElementDef>(elementDef, fields, field);
                        descriptor.setList(true);
                        def.getElements().add(descriptor);
                    }
                }
            }
            type = type.getSuperclass();
        }
        return def;
    }

    public static class Configuration {
        private Descriptor<ElementDef> descriptor;

        public Configuration(Descriptor<ElementDef> descriptor) {
            this.descriptor = descriptor;
        }

        public Configuration clientOnly() {
            descriptor.setContextType("client");
            return this;
        }

        public Configuration serverOnly() {
            descriptor.setContextType("server");
            return this;
        }

        public Configuration name(String name) {
            descriptor.getDescription().setName(name);
            return this;
        }

    }
}

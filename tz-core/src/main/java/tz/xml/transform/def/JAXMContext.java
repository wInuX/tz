package tz.xml.transform.def;

import tz.Reflector;
import tz.xml.transform.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Dmitry Shyshkin
 */
public class JAXMContext {
    private Map<Descriptor, ElementDef> definitions = new HashMap<Descriptor, ElementDef>();
    private Map<Class<?>, ElementDef> cache = new HashMap<Class<?>, ElementDef>();
    private Map<Type, XmlAdapter> adapters = new HashMap<Type, XmlAdapter>();
    private Set<Package> packages = new HashSet<Package>();

    public static JAXMContext createContext() {
        return new JAXMContext();
    }

    public Configuration register(Class<?> type) {
        packages.clear();

        ElementDef elementDef = createRootElementDef(type);
        Descriptor description = new Descriptor(new Field[0], null);
        definitions.put(description, elementDef);
        for (Package _package : packages) {
            XmlJavaTypeAdapters xmlAdapters = _package.getAnnotation(XmlJavaTypeAdapters.class);
            if (xmlAdapters != null) {
                for (XmlJavaTypeAdapter xmlAdapter : xmlAdapters.value()) {
                    Type adapterType = ((ParameterizedType)xmlAdapter.value().getGenericSuperclass()).getActualTypeArguments()[1];
                    adapters.put(adapterType, Reflector.newInstance(xmlAdapter.value()));
                }
            }
        }
        return new Configuration(description);
    }

    public ElementDef getByName(String name, Context context) {
        for (ElementDef def : definitions.values()) {
            if (def.getName().equals(name)) {
                return def;
            }
        }
        return null;
    }

    public ElementDef getByClass(Class<?> type, Context context) {
        for (ElementDef def : definitions.values()) {
            if (def.getType() == type) {
                return def;
            }
        }
        throw new IllegalStateException("No def for type " + type + " with context " + context);
    }

    public ZNode marshall(Object message, String contextType) {
        Context context = new Context(contextType, this);
        ElementDef def = getByClass(message.getClass(), context);
        return def.toNode(message, context);
    }

    public Object unmarshall(ZNode node, String contextType) {
        Context context = new Context(contextType, this);
        ElementDef def = getByName(node.getName(), context);
        if (def == null) {
            return null;
        }
        return def.fromNode(node, context);

    }

    private ElementDef createRootElementDef(Class<?> type) {
        ElementDef def = createElementDef(type);
        XmlRootElement rootElement = type.getAnnotation(XmlRootElement.class);
        if (rootElement != null) {
            if (rootElement.name().equals("##default")) {
                def.setName(type.getSimpleName());
            } else {
                def.setName(rootElement.name());
            }
        } else {
            throw new IllegalStateException(type + " not marked as @XmlRootElement");
        }
        return def;
    }
    private ElementDef createElementDef(Class<?> type) {
        if (type == null) {
            throw new IllegalStateException();
        }
        if (cache.containsKey(type)) {
            return cache.get(type);
        }
        packages.add(type.getPackage());
        ElementDef def = new ElementDef(type);
        cache.put(type, def);
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                XmlComposite composite = field.getAnnotation(XmlComposite.class);
                if (composite != null) {
                    ElementDef compositeDef = createElementDef(field.getType());
                    Map<String, String> override = new HashMap<String, String>();
                    for (XmlPropertyMapping mapping : composite.override()) {
                        override.put(mapping.propety(), mapping.name());
                    }
                    for (Map.Entry<Descriptor, AttributeDef> entry : compositeDef.getAttributes().entrySet()) {
                        Descriptor originalDescriptor = entry.getKey().copy();
                        Field[] fields = new Field[originalDescriptor.getFields().size()];
                        for (int i = 1; i < originalDescriptor.getFields().size(); ++i) {
                            fields[i] = originalDescriptor.getFields().get(i);
                        }
                        fields[0] = field;
                        Descriptor descriptor = new Descriptor(fields, originalDescriptor.getFields().get(originalDescriptor.getFields().size() - 1));
                        AttributeDef attributeDef = entry.getValue();
                        String name = originalDescriptor.getName();
                        if (override.containsKey(name)) {
                            descriptor.setName(override.get(name));
                        } else {
                            descriptor.setName(name);
                        }
                        def.getAttributes().put(descriptor, attributeDef);
                    }
                    def.getElements().putAll(compositeDef.getElements());
                    continue;
                }
                XmlAttribute attribute = field.getAnnotation(XmlAttribute.class);
                if (attribute != null) {
                    if (field.getType().getPackage() != null) {
                        packages.add(field.getType().getPackage());
                    }
                    String name = attribute.name().equals("##default") ? field.getName() : attribute.name();
                    AttributeDef attributeDef = new AttributeDef(field.getGenericType(), name);
                    XmlJavaTypeAdapter adapter = field.getAnnotation(XmlJavaTypeAdapter.class);
                    if (adapter != null) {
                        attributeDef.setAdapter(Reflector.newInstance(adapter.value()));
                    }
                    Descriptor descriptor = new Descriptor(new Field[0], field);
                    if (field.getAnnotation(ClientOnly.class) != null) {
                        descriptor.setContextType("client");
                    }
                    if (field.getAnnotation(ServerOnly.class) != null) {
                        descriptor.setContextType("server");
                    }
                    descriptor.setName(name);
                    def.getAttributes().put(descriptor, attributeDef);
                }
                XmlElement element = field.getAnnotation(XmlElement.class);
                if (element != null) {
                    if (field.getType() != List.class) {
                        ElementDef elementDef = createElementDef(field.getType());
                        String name = element.name().equals("##default") ? field.getName() : element.name();
                        Descriptor descriptor = new Descriptor(new Field[0], field);
                        descriptor.setName(name);
                        def.getElements().put(descriptor, elementDef.getType());
                    } else {
                        Class<?> itemClass = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                        ElementDef elementDef = createElementDef(itemClass);
                        String name = element.name().equals("##default") ? field.getName() : element.name();
                        Descriptor descriptor = new Descriptor(new Field[0], field);
                        descriptor.setList(true);
                        descriptor.setName(name);
                        def.getElements().put(descriptor, elementDef.getType());
                    }
                }
            }
            type = type.getSuperclass();
        }
        return def;
    }

    public Map<Class<?>, ElementDef>  getElements() {
        return cache;
    }

    public Map<Type, XmlAdapter> getAdapters() {
        return adapters;
    }

    public static class Configuration {
        private Descriptor descriptor;

        public Configuration(Descriptor descriptor) {
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
            descriptor.setName(name);
            return this;
        }

    }
}

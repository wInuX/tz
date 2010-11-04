package tz.xml.transform.def;

import tz.Reflector;
import tz.xml.transform.ZNode;

import java.util.*;

/**
 * @author Dmitry Shyshkin
 */
public class ElementDef {
    private String name;

    private Class<?> type;

    private Map<Descriptor, AttributeDef> attributes = new LinkedHashMap<Descriptor, AttributeDef>();

    private Map<Descriptor, Class<?>> elements = new HashMap<Descriptor, Class<?>>();

    public ElementDef(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Descriptor, AttributeDef> getAttributes() {
        return attributes;
    }

    public Map<Descriptor, Class<?>> getElements() {
        return elements;
    }

    public Class<?> getType() {
        return type;
    }

    @SuppressWarnings({"unchecked"})
    public Object fromNode(ZNode node, Context context) {
        Object bean = Reflector.newInstance(type);
        for (Map.Entry<Descriptor, AttributeDef> entry : this.attributes.entrySet()) {
            Descriptor attributeDescriptor = entry.getKey();
            if (!attributeDescriptor.match(context)) {
                continue;
            }
            AttributeDef attributeDef = entry.getValue();
            String attributeName = attributeDescriptor.getName();
            String attributeValue = node.getAttributes().get(attributeName);
            if (attributeValue == null) {
                continue;
            }
            attributeDescriptor.setValue(bean, attributeDef.fromString(attributeValue));
        }
        for (ZNode child : node.getChildren()) {
            for (Map.Entry<Descriptor, Class<?>> entry : elements.entrySet()) {
                Descriptor elementDescriptor = entry.getKey();
                if (!elementDescriptor.match(context)) {
                    continue;
                }
                ElementDef elementDef = context.getElementDef(entry.getValue());
                if (!elementDescriptor.getName().equals(child.getName())) {
                    continue;
                }
                Object value = elementDef.fromNode(child, context);
                if (elementDescriptor.isList()) {
                    List list = (List) elementDescriptor.getValue(bean);
                    if (list == null) {
                        elementDescriptor.setValue(bean, list = new ArrayList());
                    }
                    list.add(value);
                } else {
                    elementDescriptor.setValue(bean, value);
                }
            }
        }
        return bean;
    }

    @SuppressWarnings({"unchecked"})
    public ZNode toNode(Object bean, Context context) {
        ZNode r = new ZNode(name);

        HashMap<String, String> attributes = new LinkedHashMap<String, String>();
        for (Map.Entry<Descriptor, AttributeDef> entry : this.attributes.entrySet()) {
            Descriptor attributeDescriptor = entry.getKey();
            if (!attributeDescriptor.match(context)) {
                continue;
            }
            AttributeDef attributeDef = entry.getValue();
            String attributeName = attributeDescriptor.getName();
            Object value = attributeDescriptor.getValue(bean);
            if (value == null) {
                continue;
            }
            String attributeValue = attributeDef.toString(value);
            attributes.put(attributeName, attributeValue);
        }
        r.setAttributes(attributes);

        List<ZNode> elements = new ArrayList<ZNode>();
        for (Map.Entry<Descriptor, Class<?>> entry : this.elements.entrySet()) {
            Descriptor elementDescriptor = entry.getKey();
            if (!elementDescriptor.match(context)) {
                continue;
            }
            ElementDef elementDef = context.getElementDef(entry.getValue());
            Object value = elementDescriptor.getValue(bean);
            if (value == null) {
                continue;
            }
            if (elementDescriptor.isList()) {
                List<Object> list = (List<Object>) value;
                for (Object item : list) {
                    ZNode node = elementDef.toNode(item, context);
                    node.setName(elementDescriptor.getName());
                    elements.add(node);
                }
            } else {
                ZNode node = elementDef.toNode(value , context);
                node.setName(elementDescriptor.getName());
                elements.add(node);
            }
        }
        r.setChildren(elements);
        return r;
    }

}

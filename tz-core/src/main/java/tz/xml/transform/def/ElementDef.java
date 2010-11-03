package tz.xml.transform.def;

import tz.Reflector;
import tz.xml.transform.ZNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class ElementDef {
    private String name;

    private Class<?> type;

    private List<Descriptor<AttributeDef>> attributes = new ArrayList<Descriptor<AttributeDef>>();

    private List<Descriptor<ElementDef>> elements = new ArrayList<Descriptor<ElementDef>>();

    public ElementDef(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Descriptor<AttributeDef>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Descriptor<AttributeDef>> attributes) {
        this.attributes = attributes;
    }

    public List<Descriptor<ElementDef>> getElements() {
        return elements;
    }

    public void setElements(List<Descriptor<ElementDef>> elements) {
        this.elements = elements;
    }

    public Class<?> getType() {
        return type;
    }

    @SuppressWarnings({"unchecked"})
    public Object fromNode(ZNode node, Context context) {
        Object bean = Reflector.newInstance(type);
        for (Descriptor<AttributeDef> attributeDescriptor : this.attributes) {
            if (!attributeDescriptor.match(context)) {
                continue;
            }
            AttributeDef attributeDef = attributeDescriptor.getDescription();
            String attributeName = attributeDef.getName();
            String attributeValue = node.getAttributes().get(attributeName);
            if (attributeValue == null) {
                continue;
            }
            attributeDescriptor.setValue(bean, attributeDef.fromString(attributeValue));
        }
        for (ZNode child : node.getChildren()) {
            for (Descriptor<ElementDef> elementDescriptor : elements) {
                if (!elementDescriptor.match(context)) {
                    continue;
                }
                ElementDef elementDef = elementDescriptor.getDescription();
                if (!elementDef.getName().equals(child.getName())) {
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
        for (Descriptor<AttributeDef> attributeDescriptor : this.attributes) {
            if (!attributeDescriptor.match(context)) {
                continue;
            }
            AttributeDef attributeDef = attributeDescriptor.getDescription();
            String attributeName = attributeDef.getName();
            Object value = attributeDescriptor.getValue(bean);
            if (value == null) {
                continue;
            }
            String attributeValue = attributeDef.toString(value);
            attributes.put(attributeName, attributeValue);
        }
        r.setAttributes(attributes);

        List<ZNode> elements = new ArrayList<ZNode>();
        for (Descriptor<ElementDef> elementDescriptor : this.elements) {
            if (!elementDescriptor.match(context)) {
                continue;
            }
            ElementDef elementDef = elementDescriptor.getDescription();
            Object value = elementDescriptor.getValue(bean);
            if (value == null) {
                continue;
            }
            if (elementDescriptor.isList()) {
                List<Object> list = (List<Object>) value;
                for (Object item : list) {
                    elements.add(elementDef.toNode(item, context));
                }
            } else {
                elements.add(elementDef.toNode(value, context));
            }
        }
        r.setChildren(elements);
        return r;
    }
}

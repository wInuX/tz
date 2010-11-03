package tz.xml.transform;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class ZNode{
    private String name;
    private Map<String, String> attributes = new LinkedHashMap<String, String>();
    private List<ZNode> children = new ArrayList<ZNode>();

    public ZNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<ZNode> getChildren() {
        return children;
    }

    public void setChildren(List<ZNode> children) {
        this.children = children;
    }
}

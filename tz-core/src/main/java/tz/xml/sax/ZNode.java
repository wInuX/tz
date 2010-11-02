package tz.xml.sax;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class ZNode {
    private String name;
    private Map<String, String> attributes = new LinkedHashMap<String, String>();
    private List<ZNode> children;
    
}

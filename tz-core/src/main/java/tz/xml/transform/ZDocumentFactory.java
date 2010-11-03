package tz.xml.transform;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 * @author Dmitry Shyshkin
 */
public class ZDocumentFactory extends DefaultHandler {
    private Stack<ZNode> stack = new Stack<ZNode>();

    public void startDocument() {
        stack.clear();
        stack.add(new ZNode(null));
    }

    @Override
    public void startElement(String uri, String name, String qName, Attributes attributes) throws SAXException {
        ZNode node = new ZNode(qName);
        for (int i = 0; i < attributes.getLength(); ++i) {
            node.getAttributes().put(attributes.getQName(i), attributes.getValue(i));
        }

        ZNode parent = stack.peek();
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<ZNode>());
        }
        parent.getChildren().add(node);
        stack.push(node);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        stack.pop();
    }

    public void endDocument() {

    }

    public ZNode getDocument() {
        return stack.peek().getChildren().get(0);
    }

    public String toString(ZNode node) {
        StringBuilder sb = new StringBuilder();
        toString(sb, node, 0);
        return sb.toString();
    }

    private void toString(StringBuilder sb, ZNode node, int depth) {
        ident(sb, depth);
        sb.append("<").append(node.getName());
        if (node.getAttributes() != null && node.getAttributes().size() > 0) {
            for (Map.Entry<String, String> entry: node.getAttributes().entrySet()) {
                sb.append(' ').append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
            }
        }
        if (node.getChildren() == null || node.getChildren().size() == 0) {
            sb.append("/>");
        } else {
            sb.append(">").append("\n");
            for (ZNode child : node.getChildren()) {
                toString(sb, child, depth + 1);
                sb.append('\n');
            }
            ident(sb, depth);
            sb.append("</").append(node.getName()).append(">");
        }
    }

    private void ident(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; ++i) {
            sb.append(' ');
        }
    }
}

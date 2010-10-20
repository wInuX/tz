package tz.service;

import tz.BattleParserException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class Normalizer {
    private int p;
    private char[] chars;
    private StringBuilder r = new StringBuilder();

    public Normalizer(String content) {
        p = 0;
        chars = content.toCharArray();
    }

    public String normalize() throws BattleParserException {
        while (true) {
            skipSpaces();
            if (p == chars.length) {
                break;
            }
            if (chars[p++] != '<') {
                throw new BattleParserException();
            }
            if (chars[p] == '/') {
                ++p;
                //close tag
                String name = readName();
                if (chars[p++] != '>') {
                    throw new BattleParserException();
                }
                r.append("</").append(name).append('>');
            } else {
                String name = readName();
                Map<String, String> attributes = new LinkedHashMap<String, String>();
                boolean closed;
                while (true) {
                    skipSpaces();
                    if (p == chars.length) {
                        throw new BattleParserException();
                    }
                    if (Character.isLetterOrDigit(chars[p])) {
                        String attrName = readName();
                        if (chars[p++] != '=') {
                            throw new BattleParserException();
                        }
                        if (chars[p++] != '"') {
                            throw new BattleParserException();
                        }
                        StringBuilder sb = new StringBuilder();
                        while (chars[p]!= '"'){
                            if (chars[p] == '&') {
                                sb.append("&amp;");
                                ++p;
                            } else {
                                sb.append(chars[p++]);
                            }
                        }
                        ++p;
                        String attrValue = sb.toString();
                        attributes.put(attrName, attrValue);
                    } else if (chars[p] == '>') {
                        ++p;
                        closed = false;
                        break;
                    } else if (chars[p] == '/') {
                        ++p;
                        if (chars[p++] != '>') {
                            throw new BattleParserException();
                        }
                        closed = true;
                        break;
                    } else {
                        throw new IllegalStateException();
                    }
                }
                r.append("<").append(name);
                for (Map.Entry<String, String> entry : attributes.entrySet()) {
                    r.append(' ').append(entry.getKey()).append('=').append('"').append(entry.getValue()).append('"');
                }
                r.append(" ");
                if (closed) {
                    r.append("/>");
                } else {
                    r.append(">");
                }
            }
        }
        return r.toString();
    }

    private void skipSpaces() {
        while(p < chars.length && chars[p] <= ' ') {
            ++p;
        }
    }

    private String readName() {
        StringBuilder name = new StringBuilder();
        while (p < chars.length && (Character.isLetterOrDigit(chars[p]) || chars[p] == '-' || chars[p] == '_')) {
            name.append(chars[p++]);
        }
        return name.toString();
    }
}

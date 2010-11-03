package tz.service;

import tz.ParserException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Dmitry Shyshkin
 */
public class Normalizer {
    private int p;
    private char[] chars;
    private StringBuilder r;

    public Normalizer(String content) {
        p = 0;
        chars = content.toCharArray();
    }

    public Status normalize() throws ParserException {
        r = new StringBuilder();
        int depth = 0;
        do {
            skipSpaces();
            if (p == chars.length) {
                if (depth == 0) {
                    break;
                } else {
                    return Status.NEEDMORE;
                }
            }
            if (chars[p++] != '<') {
                throw new ParserException();
            }
            if (p == chars.length) {
                return Status.NEEDMORE;
            }
            if (chars[p] == '/') {
                --depth;
                ++p;
                if (p == chars.length) {
                    return Status.NEEDMORE;
                }
                //close tag
                String name = readName();
                skipSpaces();
                if (p == chars.length) {
                    return Status.NEEDMORE;
                }
                if (chars[p++] != '>') {
                    throw new ParserException();
                }
                shift(r, depth);
                r.append("</").append(name).append('>');
                r.append("\n");
            } else {
                String name = readName();
                Map<String, String> attributes = new LinkedHashMap<String, String>();
                boolean closed;
                while (true) {
                    skipSpaces();
                    if (p == chars.length) {
                        return Status.NEEDMORE;
                    }
                    if (Character.isLetterOrDigit(chars[p])) {
                        String attrName = readName();
                        if (p == chars.length) {
                            return Status.NEEDMORE;
                        }
                        if (chars[p++] != '=') {
                            throw new ParserException();
                        }
                        if (p == chars.length) {
                            return Status.NEEDMORE;
                        }
                        if (chars[p++] != '"') {
                            throw new ParserException();
                        }
                        if (p == chars.length) {
                            return Status.NEEDMORE;
                        }
                        StringBuilder sb = new StringBuilder();
                        while (p < chars.length && chars[p] != '"'){
                            if (chars[p] == '&') {
                                sb.append("&amp;");
                                ++p;
                            } else {
                                sb.append(chars[p++]);
                            }
                        }
                        if (p == chars.length) {
                            return Status.NEEDMORE;
                        }
                        ++p;
                        if (p == chars.length) {
                            return Status.NEEDMORE;
                        }
                        String attrValue = sb.toString();
                        attributes.put(attrName, attrValue);
                    } else if (chars[p] == '>') {
                        ++depth;
                        ++p;
                        closed = false;
                        break;
                    } else if (chars[p] == '/') {
                        ++p;
                        if (p == chars.length) {
                            return Status.NEEDMORE;
                        }
                        if (chars[p++] != '>') {
                            throw new ParserException();
                        }
                        closed = true;
                        break;
                    } else {
                        throw new ParserException();
                    }
                }
                if (closed) {
                    shift(r, depth);
                } else {
                    shift(r, depth - 1);
                }
                r.append("<").append(name);
                for (Map.Entry<String, String> entry : attributes.entrySet()) {
                    r.append(' ').append(entry.getKey()).append('=').append('"').append(entry.getValue()).append('"');
                }
                if (closed) {
                    r.append("/>");
                } else {
                    r.append(">");
                }
                r.append("\n");
            }
        } while (depth > 0);
        if (r.charAt(r.length() - 1) == '\n') {
            r.deleteCharAt(r.length() - 1);
        }
        skipSpaces();
        return p != chars.length ? Status.PARTIAL : Status.OK;
    }

    private void shift(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; ++i) {
            sb.append(' ');
        }
    }

    public String getNormalized() {
        return r.toString();
    }

    public String getParsed() {
        return new String(chars, 0, p);
    }

    public String getUnparsed() {
        return new String(chars, p, chars.length - p);
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

    public enum Status {
        OK, PARTIAL, NEEDMORE
    }
}

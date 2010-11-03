package tz.xml.transform.def;

import tz.Reflector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class Descriptor<T> {
    private String contextType;

    private boolean isList;

    private T description;

    private List<Field> fields;

    public Descriptor(T description, Field[] fields, Field field) {
        this.description = description;
        this.fields = new ArrayList<Field>();
        this.fields.addAll(Arrays.asList(fields));
        this.fields.add(field);
    }

    public T getDescription() {
        return description;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public Object getValue(Object root) {
        Object value = root;
        for (Field field : fields) {
            if (value == null) {
                return null;
            }
            value = Reflector.getField(field, value);
        }
        return value;
    }

    public void setValue(Object root, Object value) {
        Object bean = root;
        int i = 0;
        for (i = 0; i < fields.size() - 1; ++i) {
            Field field = fields.get(i);
            Object t = Reflector.getField(field, bean);
            if (t == null) {
                Reflector.setField(field, bean, t = Reflector.newInstance(field.getType()));
            }
            bean = t;
        }
        Reflector.setField(fields.get(i), bean, value);
    }

    public boolean match(Context context) {
        return contextType == null || contextType.equals(context.getType());
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }
}

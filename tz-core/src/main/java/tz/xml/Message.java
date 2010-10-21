package tz.xml;


import javax.xml.bind.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "MESSAGE")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {
    private static List<Composite> composites = new ArrayList<Composite>();

    @XmlElement(name = "MYPARAM")
    private MyParameters myParameters;

    @XmlElement(name = "GOLOC")
    private GoLocation goLocation;

    @XmlElement(name = "N")
    private Idle idle;

    @XmlElement(name = "MMP")
    private MiniMap map;

    @XmlElement(name = "L")
    private Login login;

    @XmlElement(name = "LIST")
    private ListLogin listLogin;

    @XmlElement(name = "KEY")
    private Key key;

    @XmlElement(name = "OK")
    private LoginOk loginOk;

    @XmlElement(name = "POST")
    private Post post;

    @XmlElement(name = "MMP")
    private MiniMap miniMap;

    @XmlElement(name = "AR")
    private Search search;
    
    @XmlElement(name = "GOBLD")
    private GoBuilding goBuilding;

    @XmlElement(name = "#noname")
    private String direct;

    @XmlElement(name = "BSTART")
    private BattleStart battleStart;

    @XmlElementRef
    private List<BattleAction> battleActions;

    @XmlElement(name = "BEND")
    private BattleEnd battleEnd;

    @XmlElement(name = "BATTLE")
    private Battle battle;
    
    public Message() {
    }

    public Message(Object value) {
        setValue(value);
    }

    public String getDirect() {
        return direct;
    }

    public Object getValue() {
        for (Composite composite : composites) {
            boolean empty = true;
            for (CompositeProperty property : composite.properties) {
                if (property.getLocal(this) != null) {
                    empty = false;
                }
            }
            if (!empty) {
                Object value = composite.newComposite();
                for (CompositeProperty property : composite.properties) {
                    property.toComposite(this, value);
                }
                return value;
            }

         }

        for (Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object value;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public void setValue(Object value) {
        for (Composite composite : composites) {
            if (composite.compositeClass == value.getClass()) {
                for (CompositeProperty property : composite.properties) {
                    property.toLocal(this, value);
                }
                return;
            }
        }
        for (Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            try {
                field.set(this, field.getType() == value.getClass() ? value : null);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static void registerComposite(Class<?> compositeClass) {
        Composite composite = new Composite(compositeClass);
        for (Field compositeField : compositeClass.getDeclaredFields()) {
            compositeField.setAccessible(true);
            try {
                Field localField = Message.class.getDeclaredField(compositeField.getName());
                composite.properties.add(new CompositeProperty(compositeField, localField));
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException(e);
            }
        }
        composites.add(composite);
    }

    private static class Composite {
        private Class compositeClass;
        private List<CompositeProperty> properties = new ArrayList<CompositeProperty>();

        private Composite(Class compositeClass) {
            this.compositeClass = compositeClass;
        }

        public Class getCompositeClass() {
            return compositeClass;
        }

        public List<CompositeProperty> getProperties() {
            return properties;
        }

        private Object newComposite() {
            try {
                return compositeClass.getConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new IllegalStateException(e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    private static class CompositeProperty {
        private Field compositeField;

        private Field localField;

        private CompositeProperty(Field compositeField, Field localField) {
            this.compositeField = compositeField;
            this.localField = localField;
            localField.setAccessible(true);
            compositeField.setAccessible(true);
        }

        private void toComposite(Object local, Object compisite) {
            try {
                compositeField.set(compisite, localField.get(local));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        private void toLocal(Object local, Object compisite) {
            try {
                localField.set(local, compositeField.get(compisite));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        private Object getLocal(Object local) {
            try {
                return localField.get(local);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    static {
        registerComposite(BattleActions.class);
    }
}

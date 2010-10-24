package tz.xml;

import tz.xml.adaptor.ShotDefinitionSetAdapter;
import tz.xml.adaptor.SlotSetTypeAdapter;
import tz.xml.adaptor.SlotTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Item implements Serializable{
    private static final long serialVersionUID = -428335669527727169L;

    @XmlAttribute
    private String id;

    @XmlAttribute(name = "txt")
    private String text;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "bx")
    private Integer x;

    @XmlAttribute(name = "by")
    private Integer y;

    @XmlAttribute
    private Integer count;

    @XmlAttribute(name = "massa")
    private Double weight;

    @XmlAttribute(name = "drop")
    private String drop;

    @XmlAttribute(name = "slot")
    @XmlJavaTypeAdapter(SlotTypeAdapter.class)
    private SlotPosition usedSlot;

    @XmlAttribute(name = "st")
    @XmlJavaTypeAdapter(SlotSetTypeAdapter.class)
    private Set<SlotPosition> slots;

    @XmlAttribute(name = "shot")
    @XmlJavaTypeAdapter(ShotDefinitionSetAdapter.class)
    private Set<ShotDefinition> shotDefinitions;

    @XmlAttribute(name = "OD")
    private Integer dressOd;

    @XmlAttribute(name = "rOD")
    private Integer reloadOD;

    @XmlAttribute(name = "quality")
    private Double quality;

    @XmlAttribute(name = "insert")
    private String insertTo;

    @XmlElement(name = "O")
    private List<Item> insertions;

    @XmlAttribute(name = "calibre")
    private String calibre;

    @XmlAttribute(name = "max_count")
    private Integer ammoMaxCount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public Integer getX() {
        if (x == null) {
            return null;
        }
        return x - (25 - y) / 2;
    }

    public Integer getY() {
        if (y == null) {
            return null;
        }
        return y;
    }

    public Integer getCount() {
        return count;
    }

    public Double getWeight() {
        return weight;
    }

    public String getDrop() {
        return drop;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public SlotPosition getUsedSlot() {
        return usedSlot;
    }

    public void setUsedSlot(SlotPosition usedSlot) {
        this.usedSlot = usedSlot;
    }

    public Set<SlotPosition> getSlots() {
        return slots;
    }

    public void setSlots(Set<SlotPosition> slots) {
        this.slots = slots;
    }

    public Set<ShotDefinition> getShotDefinitions() {
        return shotDefinitions;
    }

    public void setShotDefinitions(Set<ShotDefinition> shotDefinitions) {
        this.shotDefinitions = shotDefinitions;
    }

    public Integer getDressOd() {
        return dressOd;
    }

    public void setDressOd(Integer dressOd) {
        this.dressOd = dressOd;
    }

    public Integer getReloadOD() {
        return reloadOD;
    }

    public void setReloadOD(Integer reloadOD) {
        this.reloadOD = reloadOD;
    }

    public Double getQuality() {
        return quality;
    }

    public void setQuality(Double quality) {
        this.quality = quality;
    }

    public String getInsertTo() {
        return insertTo;
    }

    public void setInsertTo(String insertTo) {
        this.insertTo = insertTo;
    }

    public List<Item> getInsertions() {
        return insertions;
    }

    public void setInsertions(List<Item> insertions) {
        this.insertions = insertions;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public Integer getAmmoMaxCount() {
        return ammoMaxCount;
    }

    public void setAmmoMaxCount(Integer ammoMaxCount) {
        this.ammoMaxCount = ammoMaxCount;
    }

    public boolean isEmpty() {
        for(Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                if (field.get(this) != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return true;
    }
}

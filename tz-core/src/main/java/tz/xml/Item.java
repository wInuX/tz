package tz.xml;

import tz.Reflector;
import tz.xml.adaptor.ShotDefinitionSetAdapter;
import tz.xml.adaptor.SlotSetTypeAdapter;
import tz.xml.adaptor.SlotTypeAdapter;
import tz.xml.transform.XmlComposite;
import tz.xml.transform.XmlPropertyMapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Item implements Serializable{
    private static final long serialVersionUID = -428335669527727169L;

    @XmlAttribute
    private Id id;

    @XmlAttribute(name = "txt")
    private String text;

    @XmlAttribute(name = "name")
    private String name;

    @XmlComposite(override = {
            @XmlPropertyMapping(propety = "x", name = "bx"),
            @XmlPropertyMapping(propety = "y", name = "by")
    })
    private BattleCoordinate coordinate;

    @XmlComposite(override = {
            @XmlPropertyMapping(propety = "x", name = "X"),
            @XmlPropertyMapping(propety = "y", name = "Y")
    })
    private LocationCoordinate locationCoordinate;

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

    @XmlAttribute(name = "maxquality")
    private Double maxQuality;

    @XmlAttribute(name = "insert")
    private Id insertTo;

    @XmlElement(name = "O")
    private List<Item> insertions;

    @XmlAttribute(name = "calibre")
    private String calibre;

    @XmlAttribute(name = "max_count")
    private Integer ammoMaxCount;

    @XmlAttribute(name = "cost")
    private Double cost;

    @XmlAttribute(name = "made")
    private String madeBy;

    @XmlAttribute(name = "owner")
    private String owner;

    @XmlAttribute(name = "put_day")
    private Date startDate;

    @XmlAttribute(name = "get_day")
    private Date endDate;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public Integer getX() {
        return coordinate != null ? coordinate.getX() : null;
    }

    public Integer getY() {
        return coordinate != null ? coordinate.getY() : null;
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

    public Id getInsertTo() {
        return insertTo;
    }

    public void setInsertTo(Id insertTo) {
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

    public BattleCoordinate getCoordinate() {
        return coordinate;
    }

    public Double getMaxQuality() {
        return maxQuality;
    }

    public void setMaxQuality(Double maxQuality) {
        this.maxQuality = maxQuality;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isEmpty() {
        for(Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.getName().equals("id")) {
                continue;
            }
            if (Reflector.getField(field, this) != null) {
                return false;
            }
        }
        return true;
    }

    public boolean merge(Item item) {
        boolean modified = false;
        for(Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.getName().equals("id")) {
                continue;
            }
            Object value = Reflector.getField(field, item);
            if (value != null) {
                modified = true;
                Reflector.setField(field, this, value);
            }
        }
        return modified;
    }
}

package tz.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author Dmitry Shyshkin
 */
public class Skills {
    @XmlAttribute(name = "sk1")
    private double melee;
    @XmlAttribute(name = "sk2")
    private double light;
    @XmlAttribute(name = "sk3")
    private double medium;
    @XmlAttribute(name = "sk4")
    private double heavy;
    @XmlAttribute(name = "sk5")
    private double throwing;
    @XmlAttribute(name = "sk6")
    private double medicine;

    public double getMelee() {
        return melee;
    }

    public void setMelee(double melee) {
        this.melee = melee;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public double getMedium() {
        return medium;
    }

    public void setMedium(double medium) {
        this.medium = medium;
    }

    public double getHeavy() {
        return heavy;
    }

    public void setHeavy(double heavy) {
        this.heavy = heavy;
    }

    public double getThrowing() {
        return throwing;
    }

    public void setThrowing(double throwing) {
        this.throwing = throwing;
    }

    public double getMedicine() {
        return medicine;
    }

    public void setMedicine(double medicine) {
        this.medicine = medicine;
    }
}

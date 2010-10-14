package tz.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Dmitry Shyshkin
 */
@Entity
@Table(name = "battle")
public class BattleLog {
    @Id
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "battle_date", nullable = false)
    private Date date;

    @Column(name = "loc_x", nullable = false)
    private int locationX;

    @Column(name = "loc_y", nullable = false)
    private int locationY;

    @Column(nullable = false)
    private String xml;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
}

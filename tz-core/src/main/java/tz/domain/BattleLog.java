package tz.domain;

import javax.persistence.*;
import java.io.*;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
    @Lob
    private byte[] content;

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
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[0x4000];
        try {
            Reader reader = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(content)), "UTF-8");
            int read = 0;
            while ((read = reader.read(buf)) > 0) {
                sb.append(buf, 0, read);

            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return sb.toString();
    }

    public void setXml(String xml) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(xml.length() / 2);
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(xml.getBytes("UTF-8"));
            gzip.close();
            this.content = baos.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}

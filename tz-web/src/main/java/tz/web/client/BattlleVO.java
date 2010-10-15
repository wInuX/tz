package tz.web.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Dmitry Shyshkin
 */
public class BattlleVO implements IsSerializable {
    private long id;
    private int locationX;
    private int locationY;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

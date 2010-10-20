package tz.interceptor.game;

import com.google.inject.Singleton;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class GameState {
    private String login;

    private int x;

    private int y;

    private long locationTime;

    private long timeShift;

    private boolean inBattle = false;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(long locationTime) {
        this.locationTime = locationTime;
    }

    public long getTimeShift() {
        return timeShift;
    }

    public void setTimeShift(long timeShift) {
        this.timeShift = timeShift;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

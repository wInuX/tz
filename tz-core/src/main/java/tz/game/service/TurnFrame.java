package tz.game.service;

import tz.xml.BattleAction;
import tz.xml.Item;
import tz.xml.MyParameters;
import tz.xml.Position;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class TurnFrame {
    private BattleAction battleAction;

    private MyParameters myParameters;

    private List<Item> myItems;

    private List<Item> items;

    private int x;

    private int y;

    private Position position;

    private int remainedOD;

    public BattleAction getBattleAction() {
        return battleAction;
    }

    public void setBattleAction(BattleAction battleAction) {
        this.battleAction = battleAction;
    }

    public MyParameters getMyParameters() {
        return myParameters;
    }

    public void setMyParameters(MyParameters myParameters) {
        this.myParameters = myParameters;
    }

    public List<Item> getMyItems() {
        return myItems;
    }

    public void setMyItems(List<Item> myItems) {
        this.myItems = myItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRemainedOD() {
        return remainedOD;
    }

    public void setRemainedOD(int remainedOD) {
        this.remainedOD = remainedOD;
    }
}

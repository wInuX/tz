package tz.xml;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */

public class BattleActions {
    private BattleStart battleStart;

    private List<BattleAction> battleActions;

    private BattleEnd battleEnd;

    public BattleStart getBattleStart() {
        return battleStart;
    }

    public void setBattleStart(BattleStart battleStart) {
        this.battleStart = battleStart;
    }

    public List<BattleAction> getActions() {
        return battleActions;
    }

    public void setActions(List<BattleAction> actions) {
        this.battleActions = actions;
    }

    public BattleEnd getBattleEnd() {
        return battleEnd;
    }

    public void setBattleEnd(BattleEnd battleEnd) {
        this.battleEnd = battleEnd;
    }
}

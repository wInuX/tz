package tz.game.service;

import com.google.inject.Inject;
import tz.game.Intercept;
import tz.game.InterceptionType;
import tz.xml.Alert;
import tz.xml.Kickass;

/**
 * @author Dmitry Shyshkin
 */
public class AutoBattleStartImpl extends ArsenalServiceImpl {
    private boolean isActive = false;

    private int tryCount;

    @Inject
    private BattleService battleService;

    @Override
    public void initialize() {
        battleService.addListener(new AbstractBattleListener() {
            @Override
            public void battleStarted() {
                isActive = false;
            }
        });
    }

    @Intercept(InterceptionType.CLIENT)
    boolean onKickass(Kickass kickass) {
        isActive = true;
        tryCount = 0;
        return false;
    }

    @Intercept(InterceptionType.SERVER)
    boolean onAlert(Alert alert) {
        if (isActive &&  alert.getTxt().equals(2)) {
            if (tryCount > 10) {
                isActive = false;
                return false;
            }
            Kickass kickass = new Kickass();
            kickass.setB(100); // what is 100?
            server(kickass);
            ++tryCount;
            return true;
        }
        return false;
    }
}

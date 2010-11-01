package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.*;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(TurnPlannerServiceImpl.class)
public interface TurnPlannerService {
    boolean changePosition(Position position);

    boolean move(Direction direction);

    boolean reload(Item weapon, Item ammo);

    boolean fire(Item weapon, ShotType type, User target);

    int getMaxOD();

    int getRemainedOD();

    int[] getCrawlMovement();

    int[] getWalkMovement();

    int[] getRunMovement();

    void makeTurn();
}

package tz.game.service;

import com.google.inject.ImplementedBy;
import tz.xml.MineDirection;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(MineServiceImpl.class)
public interface MineService {
    MineMapCell[][] getMap();

    void move(MineDirection direction);

    MineMapCell getCurrentRoom();

    MineMapCell getRoom(int roomNumber);

    void addListener(MineListener listener);

    void removeListener(MineListener listener);
}

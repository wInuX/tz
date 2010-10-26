package tz.interceptor.game.service;

import com.google.inject.ImplementedBy;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(MineServiceImpl.class)
public interface MineService {
    void toRoom(int number);
}

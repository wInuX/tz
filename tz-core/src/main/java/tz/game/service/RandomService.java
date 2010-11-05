package tz.game.service;

import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@ImplementedBy(RandomServiceImpl.class)
public interface RandomService {
    int random(int range);

    <T> T randomElement(List<T> list);
}

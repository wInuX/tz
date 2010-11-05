package tz.game.service;

import com.google.inject.Singleton;

import java.util.List;
import java.util.Random;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class RandomServiceImpl implements RandomService {
    private Random random = new Random();

    public int random(int range) {
        return Math.abs(random.nextInt()) % range;
    }

    public <T> T randomElement(List<T> list) {
        return list.get(random(list.size()));
    }
}

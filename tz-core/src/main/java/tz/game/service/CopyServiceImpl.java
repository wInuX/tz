package tz.game.service;

import com.google.inject.Singleton;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@Singleton
public class CopyServiceImpl extends AbstractService implements CopyService {
    @SuppressWarnings({"unchecked"})
    public <T> T copy(T source) {
        if (source == null) {
            return null;
        }
        if (source instanceof List) {
            List<Object> r = new ArrayList<Object>();
            for (Object o : (List) source) {
                r.add(copy(o));
            }
            return (T) r;
        }
        try {
            Class<? extends Object> type = source.getClass();
            Object target = type.getConstructor().newInstance();
            while (type != Object.class) {
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(target, field.get(source));
                }
                type = type.getSuperclass();
            }
            return (T) target;
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
}

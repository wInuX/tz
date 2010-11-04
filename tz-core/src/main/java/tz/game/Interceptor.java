package tz.game;

/**
 * @author Dmitry Shyshkin
 */
public interface Interceptor<T> {
    boolean intercept(String original, T message);
}

package tz.game;

/**
 * @author Dmitry Shyshkin
 */
public interface Interceptor {
    boolean intercept(String original, Object message);
}

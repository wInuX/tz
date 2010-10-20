package tz.interceptor.game;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Dmitry Shyshkin
 */
public class MethodBasedInterceptor implements Interceptor {
    private Method method;
    private Object instance;

    public MethodBasedInterceptor(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public boolean intercept(String original, Object message) {
        try {
            if (method.getParameterTypes().length == 2) {
                return (Boolean) method.invoke(instance, original, message);
            } else {
                return (Boolean) method.invoke(instance, original);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}

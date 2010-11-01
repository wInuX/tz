package tz.game.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
public class Notificator {
    @SuppressWarnings({"unchecked"})
    public static <T> T createNotificator(Class<T> type, final List<T> listeners) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                for (T listener : new ArrayList<T>(listeners)) {
                    method.invoke(listener, args);
                }
                return null;
            }
        });
    }
}

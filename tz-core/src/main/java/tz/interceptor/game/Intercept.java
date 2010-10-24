package tz.interceptor.game;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dmitry Shyshkin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Intercept {
    InterceptionType value() default InterceptionType.SERVER;

    InterceptorPriority priority() default InterceptorPriority.DEFAULT;
}

package tz.interceptor;

/**
* @author Dmitry Shyshkin
*/
public interface LinkControl {
    void write(char[] buf, int length);

    void close();
}

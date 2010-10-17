package tz.interceptor;

/**
* @author Dmitry Shyshkin
*/
public interface LinkListener {
    void read(char[] buf, int length);

    void closed();
}

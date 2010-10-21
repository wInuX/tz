package tz.interceptor;

/**
 * @author Dmitry Shyshkin
 */
public interface InterceptedConnection {
    LinkControl getSlaveMasterControl();

    LinkControl getMasterSlaveControl();

    void setSlaveMasterListener(LinkListener listener);

    void setMasterSlaveListener(LinkListener listener);
}

package tz.game.scenario;

/**
 * @author Dmitry Shyshkin
 */
public class Condition {
    private boolean active;
    private final Object monitor = new Object();
    
    public void await() throws InterruptedException {
        synchronized (monitor) {
            while (!active) {
                monitor.wait();
            }
        }
    }

    public void signal() {
        synchronized (monitor) {
            active = true;
            monitor.notifyAll();
        }
    }
}

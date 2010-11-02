package tz.game.scenario;

/**
 * @author Dmitry Shyshkin
 */
public class Condition {
    private boolean active;
    private final Object monitor = Scenario.getMonitor();
    private RuntimeException exception;

    public void await() throws InterruptedException {
        synchronized (monitor) {
            while (!active) {
                monitor.wait();
            }
            if (exception != null) {
                throw exception;
            }
        }
    }

    public void signal() {
        synchronized (monitor) {
            active = true;
            monitor.notifyAll();
        }
    }

    public void signal(RuntimeException exception) {
        synchronized (monitor) {
            this.exception = exception;
            active = true;
            monitor.notifyAll();
        }
    }
}

package tz.scenario;

/**
 * @author Dmitry Shyshkin
 */
public interface StepCallback {
    String SUCCESS = "succces";
    String ERROR = "error";

    void completed(String status);
}

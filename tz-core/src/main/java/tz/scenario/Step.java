package tz.scenario;

/**
 * @author Dmitry Shyshkin
 */
public abstract class Step {

    protected abstract void execute(Scenario scenario, StepCallback callback);

    protected abstract void cleanup();
}

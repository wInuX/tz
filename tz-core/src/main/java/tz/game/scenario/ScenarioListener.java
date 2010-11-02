package tz.game.scenario;

/**
 * @author Dmitry Shyshkin
 */
public interface ScenarioListener {
    void scenarioLoaded(Scenario scenario);

    void scenarioUnloaded(Scenario scenario);
}

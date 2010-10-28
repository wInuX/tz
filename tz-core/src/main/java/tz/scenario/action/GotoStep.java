package tz.scenario.action;

import tz.scenario.Scenario;
import tz.scenario.Step;
import tz.scenario.StepCallback;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Dmitry Shyshkin
 */
@XmlRootElement(name = "change-state")
public class GotoStep extends Step {
    @XmlAttribute(name = "name")
    private String name;

    @Override
    protected void execute(Scenario scenario, StepCallback callback) {
        scenario.execute(name);
    }

    @Override
    protected void cleanup() {

    }
}

package tz.scenario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Shyshkin
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "scenario")
public class Scenario {
    @XmlElement(name = "state")
    private List<State> states;

    private List<Frame> frames = new ArrayList<Frame>();

    private StepCallback callback = new StepCallback() {
        public void completed(String status) {
            if (StepCallback.SUCCESS.equals(status)) {
                executeNext();
            } else {
                stop();
            }
        }
    };


    public void start() {
        State undefined = getState("undefined");
        frames.add(new Frame(undefined));
    }

    private void execute() {
        if (frames.size() == 0) {
            stop();
            return;
        }
        Frame frame = frames.get(frames.size() - 1);
        frame.getCurrentAction().execute(this, callback);
    }

    public void execute(String state) {
        
    }

    public void executeNext() {
        while (frames.size() > 0) {
            Frame frame = frames.get(frames.size() - 1);
            if (frame.next() == null) {
                frames.remove(frames.size() - 1);
            }
        }
        execute();
    }


    public void stop() {

    }

    private State getState(String name) {
        for (State state : states) {
            if (name.equals(state.getName())) {
                return state;
            }
        }
        return null;
    }
}

package tz.scenario;

/**
 * @author Dmitry Shyshkin
 */
public class Frame {
    private State state;

    private Step currentAction;

    private int currentIndex;

    public Frame(State state) {
        this.state = state;
        currentIndex = 0;
        currentAction = state.getActions().get(currentIndex);
    }

    public State getState() {
        return state;
    }

    public Step getCurrentAction() {
        return currentAction;
    }

    public Step next() {
        ++currentIndex;
        if (currentIndex < state.getActions().size()) {
            currentAction = state.getActions().get(currentIndex);
            return currentAction;
        } else {
            currentAction = null;
            return null;
        }
    }
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to a forward M state
 */
public class InsertForwardTransition extends InsertTransition {
    /**
     * Create a new InsertForwardTransition
     *
     * @param toState The state to which this transition goes
     */
    public InsertForwardTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to the E state
 */
public class EndForwardTransition extends EndTransition {
    /**
     * Create a new EndForwardTransition
     */
    public EndForwardTransition() {
        super(HMMState.END);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

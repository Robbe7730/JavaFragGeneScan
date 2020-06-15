package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to the E' state
 */
public class EndReverseTransition extends EndTransition {
    /**
     * Create a new EndReverseTransition
     */
    public EndReverseTransition() {
        super(HMMState.END_REVERSE);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

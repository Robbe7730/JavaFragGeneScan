package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to the R state
 */
public class NonCodingTransition extends Transition {
    /**
     * Create a new NonCodingTransition
     */
    public NonCodingTransition() {
        super(HMMState.NON_MATCHING);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

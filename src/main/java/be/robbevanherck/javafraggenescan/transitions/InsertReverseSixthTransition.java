package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to the I6' state
 */
public class InsertReverseSixthTransition extends MatchReverseTransition {
    /**
     * Create a new InsertReverseSixthTransition
     */
    public InsertReverseSixthTransition() {
        super(HMMState.INSERT_REVERSE_6);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

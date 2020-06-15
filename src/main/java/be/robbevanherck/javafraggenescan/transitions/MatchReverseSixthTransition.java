package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to the M6' state
 */
public class MatchReverseSixthTransition extends MatchReverseTransition {
    /**
     * Create a new MatchReverseSixthTransition
     */
    public MatchReverseSixthTransition() {
        super(HMMState.MATCH_REVERSE_6);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the M1' state
 */
public class MatchReverseFirstTransition extends MatchReverseTransition {
    /**
     * Create a new MatchReverseFirstTransition
     */
    public MatchReverseFirstTransition() {
        super(HMMState.MATCH_REVERSE_1);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        return 0;
    }
}

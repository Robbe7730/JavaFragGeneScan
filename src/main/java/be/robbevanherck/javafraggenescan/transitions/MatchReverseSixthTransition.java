package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

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
    public double calculateProbability(ViterbiStep currentStep) {
        //TODO
        return 0;
    }
}

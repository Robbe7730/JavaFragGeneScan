package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the M1 state
 */
public class MatchForwardFirstTransition extends MatchForwardTransition {
    /**
     * Create a new MatchForwardFirstTransition
     */
    public MatchForwardFirstTransition() {
        super(HMMState.MATCH_1);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        //TODO
        return 0;
    }
}

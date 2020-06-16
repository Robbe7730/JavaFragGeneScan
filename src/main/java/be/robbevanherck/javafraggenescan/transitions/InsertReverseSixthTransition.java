package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the I6' state
 */
public class InsertReverseSixthTransition extends InsertReverseTransition {
    /**
     * Create a new InsertReverseSixthTransition
     */
    public InsertReverseSixthTransition() {
        super(HMMState.INSERT_REVERSE_6);
    }

    @Override
    protected double getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, ViterbiStep currStep) {
        return super.getProbabilityFromMatch(parameters, previous, currStep) *              // The probability is the same as the other I states
                parameters.getOuterTransitionProbability(HMMOuterTransition.GENE_GENE);     // Except for the extra outer transition probability
    }
}

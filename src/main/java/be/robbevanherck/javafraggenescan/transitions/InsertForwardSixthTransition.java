package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the I6 state
 */
public class InsertForwardSixthTransition extends InsertForwardTransition {
    /**
     * Create a new InsertForwardSixthTransition
     */
    public InsertForwardSixthTransition() {
        super(HMMState.INSERT_6);
    }

    @Override
    protected PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        PathProbability ret =  super.getProbabilityFromMatch(parameters, previous, currentStep);                                // The probability is the same as the other I states
        ret.setProbability(ret.getProbability() * parameters.getOuterTransitionProbability(HMMOuterTransition.GENE_GENE));      // Except for the extra outer transition probability
        return ret;
    }
}

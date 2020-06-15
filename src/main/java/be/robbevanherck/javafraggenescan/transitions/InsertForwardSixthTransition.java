package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMOuterTransition;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

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
    protected double getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        return super.getProbabilityFromMatch(parameters, previous, currentStep) *           // The probability is the same as the other I states
                parameters.getOuterTransitionProbability(HMMOuterTransition.GENE_GENE);     // Except for the extra outer transition probability
    }
}

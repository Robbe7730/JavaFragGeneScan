package be.robbevanherck.javafraggenescan.transitions;

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
    public double calculateProbability(ViterbiStep currentStep) {
        //TODO
        return 0;
    }
}

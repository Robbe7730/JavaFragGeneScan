package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

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
    public double calculateProbability(ViterbiStep currentStep) {
        //TODO
        return 0;
    }
}

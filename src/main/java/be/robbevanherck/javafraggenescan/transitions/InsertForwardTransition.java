package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

/**
 * Represents a transition to a forward M state
 */
public class InsertForwardTransition extends InsertTransition {
    /**
     * Create a new InsertForwardTransition
     *
     * @param toState The state to which this transition goes
     */
    public InsertForwardTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        //TODO
        return 0;
    }
}

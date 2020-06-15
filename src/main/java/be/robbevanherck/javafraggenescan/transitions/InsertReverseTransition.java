package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

/**
 * Represents a transition to a reverse I state
 */
public class InsertReverseTransition extends InsertTransition {
    /**
     * Create a new InsertReverseTransition
     *
     * @param toState The state to which this transition goes
     */
    public InsertReverseTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        //TODO
        return 0;
    }
}

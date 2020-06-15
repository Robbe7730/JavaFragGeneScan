package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

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
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

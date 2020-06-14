package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to a reverse I state
 */
public class InsertReverseTransition extends InsertTransition {
    /**
     * Create a new InsertReverseTransition
     *
     * @param toState The state to which this transition goes
     */
    InsertReverseTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

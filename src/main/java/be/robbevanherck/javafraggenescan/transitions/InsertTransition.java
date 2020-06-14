package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to a I (forward or reverse) state
 */
public abstract class InsertTransition extends Transition {
    /**
     * Create a new InsertTransition
     *
     * @param toState The state to which this transition goes
     */
    InsertTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public abstract void calculateStateTransition(ViterbiStep previous, ViterbiStep curr);
}

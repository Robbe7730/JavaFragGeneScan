package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to an S or E state, forward or reverse
 */
public abstract class StartEndTransition extends Transition {
    /**
     * Create a new StartEndTransition
     *
     * @param toState The state to which this transition goes
     */
    StartEndTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public abstract void calculateStateTransition(ViterbiStep previous, ViterbiStep curr);
}

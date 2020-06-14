package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to an S or S' state
 */
public abstract class StartTransition extends StartEndTransition {
    /**
     * Create a new StartTransition
     *
     * @param toState The state to which this transition goes
     */
    StartTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the S' state
 */
public class StartReverseTransition extends EndTransition {
    /**
     * Create a new EndTransition
     *
     * @param toState The state to which this transition goes
     */
    StartReverseTransition(StateEnum toState) {
        super(StateEnum.START_REVERSE);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

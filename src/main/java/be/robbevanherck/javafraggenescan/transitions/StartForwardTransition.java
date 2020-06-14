package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents the transition to the S state
 */
public class StartForwardTransition extends EndTransition {
    /**
     * Create a new StartForwardTransition
     */
    StartForwardTransition() {
        super(StateEnum.START);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

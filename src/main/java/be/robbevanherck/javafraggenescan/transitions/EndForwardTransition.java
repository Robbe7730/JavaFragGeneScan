package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the E state
 */
public class EndForwardTransition extends EndTransition {
    /**
     * Create a new EndForwardTransition
     */
    EndForwardTransition() {
        super(StateEnum.END);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

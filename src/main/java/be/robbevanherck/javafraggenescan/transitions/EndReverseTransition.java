package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the E' state
 */
public class EndReverseTransition extends EndTransition {
    /**
     * Create a new EndReverseTransition
     */
    EndReverseTransition() {
        super(StateEnum.END_REVERSE);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

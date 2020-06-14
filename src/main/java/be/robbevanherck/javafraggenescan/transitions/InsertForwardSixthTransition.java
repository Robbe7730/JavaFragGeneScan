package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the I6 state
 */
public class InsertForwardSixthTransition extends InsertForwardTransition {
    /**
     * Create a new InsertForwardSixthTransition
     */
    InsertForwardSixthTransition() {
        super(StateEnum.INSERT_6);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

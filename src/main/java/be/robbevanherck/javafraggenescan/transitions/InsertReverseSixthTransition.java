package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the I6' state
 */
public class InsertReverseSixthTransition extends MatchReverseTransition {
    /**
     * Create a new InsertReverseSixthTransition
     */
    InsertReverseSixthTransition() {
        super(StateEnum.INSERT_REVERSE_6);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the R state
 */
public class NonCodingTransition extends Transition {
    /**
     * Create a new NonCodingTransition
     */
    NonCodingTransition() {
        super(StateEnum.NON_MATCHING);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

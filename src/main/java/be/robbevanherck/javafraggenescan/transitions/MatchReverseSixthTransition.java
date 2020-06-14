package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the M6' state
 */
public class MatchReverseSixthTransition extends MatchReverseTransition {
    /**
     * Create a new MatchReverseSixthTransition
     */
    MatchReverseSixthTransition() {
        super(StateEnum.MATCH_REVERSE_6);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

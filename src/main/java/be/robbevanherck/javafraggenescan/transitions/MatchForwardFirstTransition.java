package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to the M1 state
 */
public class MatchForwardFirstTransition extends MatchForwardTransition {
    /**
     * Create a new MatchForwardFirstTransition
     */
    MatchForwardFirstTransition() {
        super(StateEnum.MATCH_1);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

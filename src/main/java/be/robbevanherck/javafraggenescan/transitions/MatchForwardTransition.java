package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to a forward M state
 */
public class MatchForwardTransition extends MatchTransition {
    /**
     * Create a new MatchForwardTransition
     *
     * @param toState The state to which this transition goes
     */
    MatchForwardTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

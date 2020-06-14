package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to a reverse M state
 */
public class MatchReverseTransition extends MatchTransition {
    /**
     * Create a new MatchReverseTransition
     *
     * @param toState The state to which this transition goes
     */
    MatchReverseTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public void calculateStateTransition(ViterbiStep previous, ViterbiStep curr) {
        //TODO
    }
}

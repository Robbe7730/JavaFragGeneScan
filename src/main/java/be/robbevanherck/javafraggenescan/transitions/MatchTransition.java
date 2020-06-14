package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * Represents a transition to an M (forward or reverse) state
 */
public abstract class MatchTransition extends Transition {
    /**
     * Create a new MatchTransition
     *
     * @param toState The state to which this transition goes
     */
    MatchTransition(StateEnum toState) {
        super(toState);
    }

    @Override
    public abstract void calculateStateTransition(ViterbiStep previous, ViterbiStep curr);
}

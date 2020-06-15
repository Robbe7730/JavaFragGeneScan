package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to an M (forward or reverse) state
 */
public abstract class MatchTransition extends Transition {
    /**
     * Create a new MatchTransition
     *
     * @param toState The state to which this transition goes
     */
    MatchTransition(HMMState toState) {
        super(toState);
    }
}

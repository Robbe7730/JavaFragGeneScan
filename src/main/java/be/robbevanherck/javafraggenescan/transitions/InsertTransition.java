package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to a I (forward or reverse) state
 */
public abstract class InsertTransition extends Transition {
    /**
     * Create a new InsertTransition
     *
     * @param toState The state to which this transition goes
     */
    InsertTransition(HMMState toState) {
        super(toState);
    }

}

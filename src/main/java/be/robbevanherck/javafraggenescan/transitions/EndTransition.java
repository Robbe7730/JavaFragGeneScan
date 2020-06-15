package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to the E or E' state
 */
public abstract class EndTransition extends StartEndTransition {
    /**
     * Create a new EndTransition
     *
     * @param toState The state to which this transition goes
     */
    EndTransition(HMMState toState) {
        super(toState);
    }
}

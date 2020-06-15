package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to an S or S' state
 */
public abstract class StartTransition extends StartEndTransition {
    /**
     * Create a new StartTransition
     *
     * @param toState The state to which this transition goes
     */
    StartTransition(HMMState toState) {
        super(toState);
    }
}

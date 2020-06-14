package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entity.ViterbiStep;
import be.robbevanherck.javafraggenescan.enums.StateEnum;

/**
 * The top-level interface for transition handlers
 */
public abstract class Transition {
    private final StateEnum toState;

    /**
     * Create a new TransitionHandler
     * @param toState The state to which this transition goes
     */
    Transition(StateEnum toState) {
        this.toState = toState;
    }
    /**
     * Calculate a step in the Viterbi algorithm, altering the current ViterbiStep
     * @param previous The result of the previous step in the Viterbi algorithm
     * @param curr The current step in the Viterbi algorithm, to be filled in
     */
    public abstract void calculateStateTransition(ViterbiStep previous, ViterbiStep curr);

    /**
     * Returns what state this transition goes to
     * @return The state this transition goes to
     */
    public StateEnum getToState() {
        return toState;
    }
}

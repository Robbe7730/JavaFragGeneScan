package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * The top-level interface for transition handlers
 */
public abstract class Transition {
    protected final HMMState toState;

    /**
     * Create a new TransitionHandler
     * @param toState The state to which this transition goes
     */
    Transition(HMMState toState) {
        this.toState = toState;
    }

    /**
     * Calculate a step in the Viterbi algorithm, altering the current ViterbiStep
     * @param params The parameters for the HMM
     * @param curr The current step in the Viterbi algorithm, to be filled in
     */
    public void calculateStateTransition(HMMParameters params, ViterbiStep curr) {
        curr.setValueFor(this.toState, calculateProbability());
    }

    /**
     * Returns what state this transition goes to
     * @return The state this transition goes to
     */
    public HMMState getToState() {
        return toState;
    }

    /**
     * Calculate the probability of this transition
     * @return The probability
     */
    public abstract float calculateProbability();
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

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

    protected double getProbabilityFromMatchState(HMMState mState, ViterbiStep previous) {
        return previous.getValueFor(mState) *                                                           // Probability to be in mState state at t-1
                previous.getParameters().getOuterTransitionProbability(HMMOuterTransition.GENE_END);    // Probability of outer transition G -> E
    }
}

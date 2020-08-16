package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

import java.math.BigDecimal;

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

    protected PathProbability getProbabilityFromMatchState(HMMState mState, ViterbiStep previous) {
        BigDecimal probability = previous.getProbabilityFor(mState).multiply(                                                // Probability to be in mState state at t-1
                                    previous.getParameters().getOuterTransitionProbability(HMMOuterTransition.GENE_END));    // Probability of outer transition G -> E
        return new PathProbability(mState, probability);
    }

    @Override
    protected Triple<AminoAcid> getCodonStartingOrEndingAtT(ViterbiStep currentStep) {
        return getCodonEndingAtT(currentStep);
    }
}

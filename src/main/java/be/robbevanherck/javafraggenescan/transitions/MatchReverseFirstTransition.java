package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the M1' state
 */
public class MatchReverseFirstTransition extends MatchReverseTransition {
    /**
     * Create a new MatchReverseFirstTransition
     */
    public MatchReverseFirstTransition() {
        super(HMMState.MATCH_REVERSE_1);
    }

    @Override
    protected double getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        return super.getProbabilityFromMatch(parameters, previous, codonEndingAtT) *        // The probability of staying in the M state is the same as the other M states
                parameters.getOuterTransitionProbability(HMMOuterTransition.GENE_GENE);     // times the probability to remain in outer state G
    }
}

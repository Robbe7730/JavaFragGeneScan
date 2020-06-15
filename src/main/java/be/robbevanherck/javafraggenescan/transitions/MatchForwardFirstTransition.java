package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the M1 state
 */
public class MatchForwardFirstTransition extends MatchForwardTransition {
    /**
     * Create a new MatchForwardFirstTransition
     */
    public MatchForwardFirstTransition() {
        super(HMMState.MATCH_1);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        // If we're in the first 2 steps, we can't be in an M state
        if (previous.getPrevious() == null) {
            return 0;
        }

        Triple<AminoAcid> codonEndingAtT = new Triple<>(
                previous.getPrevious().getInput(),
                previous.getInput(),
                currentStep.getInput()
        );

        /* FROM START STATE */
        double bestValue = previous.getValueFor(HMMState.START) *               // Probability to be in a START state at t-1
            parameters.getMatchEmissionProbability(HMMState.MATCH_1, codonEndingAtT);   // Probability of emission of M

        /* FROM M6 STATE */

        bestValue = Math.max(bestValue,
                previous.getValueFor(HMMState.MATCH_6) *                                    // Probability to be in a M6 state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.GENE_GENE) *    // Probability of an outer transition G -> G
                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_MATCH) *  // Probability of an inner transition M -> M
                parameters.getMatchEmissionProbability(HMMState.MATCH_1, codonEndingAtT)            // Probability of emission of M1
        );

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        bestValue = Math.max(bestValue, getProbabilityThroughDeletions(parameters, previous, codonEndingAtT));

        /* FROM I STATE */

        bestValue = Math.max(bestValue, getProbabilityFromInsertion(parameters, previous, currentStep));

        return bestValue;
    }
}

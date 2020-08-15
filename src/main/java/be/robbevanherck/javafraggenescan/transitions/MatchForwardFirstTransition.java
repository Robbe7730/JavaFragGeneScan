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
    public PathProbability calculatePathProbability(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        /* FROM START STATE */
        double probability = previous.getProbabilityFor(HMMState.START) *                   // Probability to be in a START state at t-1
                parameters.getMatchEmissionProbability(HMMState.MATCH_1, getCodonEndingAtT(currentStep));   // Probability of emission of M
        PathProbability bestValue = new PathProbability(HMMState.START, probability);

        /* FROM M6 STATE */

        probability = previous.getProbabilityFor(HMMState.MATCH_6) *                                        // Probability to be in a M6 state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.GENE_GENE) *                    // Probability of an outer transition G -> G
                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_MATCH) *                  // Probability of an inner transition M -> M
                parameters.getMatchEmissionProbability(HMMState.MATCH_1, getCodonEndingAtT(currentStep));   // Probability of emission of M1
        bestValue = PathProbability.max(bestValue,
            new PathProbability(HMMState.MATCH_6, probability)
        );

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        bestValue = PathProbability.max(bestValue, getProbabilityThroughDeletions(parameters, previous, getCodonEndingAtT(currentStep)));

        /* FROM I STATE */

        bestValue = PathProbability.max(bestValue, getProbabilityFromInsertion(parameters, currentStep));

        return bestValue;
    }
}

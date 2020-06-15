package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to a forward M state, except the M1 state
 */
public class MatchForwardTransition extends MatchTransition {
    /**
     * Create a new MatchForwardTransition
     *
     * @param toState The state to which this transition goes
     */
    public MatchForwardTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        // If we're in the first 2 steps, we can't be in an M(2-6) state
        if (previous.getPrevious() == null) {
            // currentStep.getPrevious() can never be null, as the first step is initialized and not calculated
            return 0;
        }

        Triple<AminoAcid> codonEndingAtT = new Triple<>(
                previous.getPrevious().getInput(),
                previous.getInput(),
                currentStep.getInput()
        );

        /* FROM M STATE */

        HMMState previousMState = HMMState.previousState(toState);
        double bestValue = previous.getValueFor(previousMState) *                           // Probability to be in state previousMState at t-1
                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_MATCH) *  // Probability for transition M -> M
                parameters.getMatchEmissionProbability(toState, codonEndingAtT);                    // Probability for an emission of M

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        bestValue = Math.max(bestValue, getProbabilityThroughDeletions(parameters, previous, codonEndingAtT));

        /* FROM I STATE */

        bestValue = Math.max(bestValue, getProbabilityFromInsertion(parameters, previous, currentStep));

        return bestValue;
    }

    @Override
    protected boolean isStopCodon(Triple<AminoAcid> codonWithoutInsertions) {
        return (codonWithoutInsertions.getFirstValue() == AminoAcid.T && (                                                              // The first acid is always T
                (codonWithoutInsertions.getSecondValue() == AminoAcid.A && codonWithoutInsertions.getThirdValue() == AminoAcid.A) ||    // TAA
                (codonWithoutInsertions.getSecondValue() == AminoAcid.A && codonWithoutInsertions.getThirdValue() == AminoAcid.G) ||    // TAG
                (codonWithoutInsertions.getSecondValue() == AminoAcid.G && codonWithoutInsertions.getThirdValue() == AminoAcid.A)       // TGA
        ));
    }

    /**
     * Calculate the probability of going from an M state, through some amount of D states to the destination M state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param codonEndingAtT The coding starting at t-2 to t
     * @return The probability
     */
    protected double getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        double bestValue = 0;
        if (!parameters.wholeGenome()) {
            HMMState currState = HMMState.previousState(toState);
            int numD = 1;
            while (currState != toState) {
                bestValue = Math.max(bestValue,
                        previous.getValueFor(currState) *                                                                               // Probability to be in currState at t-1
                                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_DELETE) * 0.25 *                      // Probability of transition + emission of M -> D
                                Math.pow((parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_DELETE) * 0.25), numD) *   // Probability of numD transitions and emissions of D -> D
                                parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_MATCH) *                             // Probability of transition of M -> D
                                parameters.getMatchEmissionProbability(currState, codonEndingAtT)                                               // Probability of emission of M
                );

                numD++;
                currState = HMMState.previousState(currState);
            }
        }
        return bestValue;
    }

}

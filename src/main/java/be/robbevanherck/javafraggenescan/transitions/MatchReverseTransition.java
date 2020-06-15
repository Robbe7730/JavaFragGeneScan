package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to a reverse M state
 */
public class MatchReverseTransition extends MatchTransition {
    /**
     * Create a new MatchReverseTransition
     *
     * @param toState The state to which this transition goes
     */
    public MatchReverseTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        double bestValue = 0;
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        // If we're in the first 2 steps, we can't be in an M(2-6)' state
        if (previous.getPrevious() == null) {
            // currentStep.getPrevious() can never be null, as the first step is initialized and not calculated
            return 0;
        }

        Triple<AminoAcid> codonEndingAtT = new Triple<>(
                previous.getPrevious().getInput(),
                previous.getInput(),
                currentStep.getInput()
        );

        /* FROM START' STATE */

        if (canComeFromStartState(currentStep)) {
            bestValue = previous.getValueFor(HMMState.START_REVERSE) *                              // Probability to be in the Start' state at t-1
                        parameters.getReverseMatchEmissionProbability(toState, codonEndingAtT);     // Probability of emission from M' state (transition is guaranteed)
        }

        /* FROM M' STATE */

        bestValue = Math.max(bestValue,
                previous.getValueFor(HMMState.previousState(toState)) *                         // Probability to be in the M' state at t-1
                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_MATCH) *      // Probability of inner transition M -> M
                parameters.getReverseMatchEmissionProbability(toState, codonEndingAtT)          // Probability of emission from M' sate
        );

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        bestValue = Math.max(bestValue, getProbabilityThroughDeletions(parameters, previous, codonEndingAtT));

        /* FROM I STATE */

        bestValue = Math.max(bestValue, getProbabilityFromInsertion(parameters, previous, currentStep));
        return bestValue;
    }

    protected boolean canComeFromStartState(ViterbiStep currentStep) {
        // To be coming from a start state, we need to check the reverse of an end state
        return (toState == HMMState.INSERT_REVERSE_1 || toState == HMMState.INSERT_REVERSE_4) &&
                (isStopCodon(new Triple<>(
                        currentStep.getInput(),
                        currentStep.getPrevious().getInput(),
                        currentStep.getPrevious().getPrevious().getInput()
                )));
    }

    @Override
    protected boolean isStopCodon(Triple<AminoAcid> codon) {
        return (codon.getThirdValue() == AminoAcid.A &&                                                 // The last acid is always A
                (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.T) ||      // TTA
                (codon.getFirstValue() == AminoAcid.C && codon.getSecondValue() == AminoAcid.T) ||      // CTA
                (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.C)         // TCA
        );
    }

    /**
     * Calculate the probability of going from an M' state, through some amount of D' states to the destination M' state
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
                                parameters.getReverseMatchEmissionProbability(currState, codonEndingAtT)                                // Probability of emission of M
                );

                numD++;
                currState = HMMState.previousState(currState);
            }
        }
        return bestValue;
    }
}

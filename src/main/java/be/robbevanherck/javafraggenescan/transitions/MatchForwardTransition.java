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

        // If we're in the first 3 steps, we can't be in an M state
        if (currentStep.getPrevious().getPrevious() == null) {
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
                parameters.getMatchEmissionFor(toState, codonEndingAtT);                    // Probability for an emission of M

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        if (!parameters.wholeGenome()) {
            HMMState currState = previousMState;
            int numD = 1;
            while (currState != toState) {
                bestValue = Math.max(bestValue,
                        previous.getValueFor(currState) *                                                                               // Probability to be in currState at t-1
                                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_DELETE) * 0.25 *                      // Probability of transition + emission of M -> D
                                Math.pow((parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_DELETE) * 0.25), numD) *   // Probability of numD transitions and emissions of D -> D
                                parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_MATCH) *                             // Probability of transition of M -> D
                                parameters.getMatchEmissionFor(currState, codonEndingAtT)                                               // Probability of emission of M
                );

                numD++;
                currState = HMMState.previousState(currState);
            }
        }

        /* FROM I STATE */

        // This is the codon that is present if you ignore the insertions
        Triple<AminoAcid> codonWithoutInsertions = new Triple<>(
                AminoAcid.INVALID,
                AminoAcid.INVALID,
                AminoAcid.INVALID
        );

        Pair<AminoAcid> aminoAcidBeforeInsert = currentStep.getAminoAcidsBeforeInsert(HMMState.insertStateFor(toState));

        // Depending on what state we're going to find how the codon would have looked
        if ((aminoAcidBeforeInsert != null) && (toState == HMMState.MATCH_3 || toState == HMMState.MATCH_6)) {
            codonWithoutInsertions = aminoAcidBeforeInsert.append(currentStep.getInput());
        } else if ((aminoAcidBeforeInsert != null) && (toState == HMMState.MATCH_2 || toState == HMMState.MATCH_5)) {
            codonWithoutInsertions = new Triple<>(
                    aminoAcidBeforeInsert.getSecondValue(),
                    currentStep.getInput(),
                    currentStep.getNextInput()
            );
        }

        // If it's a stop codon, we can't be in an M state, but should be in an END state
        if (!isStopCodon(codonWithoutInsertions)) {
            bestValue = Math.max(bestValue,
                    previous.getValueFor(HMMState.insertStateFor(toState)) *                            // Probability to be in state Ix at t-1
                    parameters.getInnerTransitionProbability(HMMInnerTransition.INSERT_MATCH) * 0.25    // Probability for transmission + emission for I -> M
            );
        }

        return bestValue;
    }

    private boolean isStopCodon(Triple<AminoAcid> codonWithoutInsertions) {
        return (codonWithoutInsertions.getFirstValue() == AminoAcid.T && (                                                              // The first acid is always T
                (codonWithoutInsertions.getSecondValue() == AminoAcid.A && codonWithoutInsertions.getThirdValue() == AminoAcid.A) ||    // TAA
                (codonWithoutInsertions.getSecondValue() == AminoAcid.A && codonWithoutInsertions.getThirdValue() == AminoAcid.G) ||    // TAG
                (codonWithoutInsertions.getSecondValue() == AminoAcid.G && codonWithoutInsertions.getThirdValue() == AminoAcid.A)       // TGA
        ));
    }

}

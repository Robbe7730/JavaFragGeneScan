package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to an M (forward or reverse) state
 */
public abstract class MatchTransition extends Transition {
    /**
     * Create a new MatchTransition
     *
     * @param toState The state to which this transition goes
     */
    MatchTransition(HMMState toState) {
        super(toState);
    }

    /**
     * Calculate the probability of going from an I' state to the destination M' state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param currentStep The current Viterbi step
     * @return The probability
     */
    protected double getProbabilityFromInsertion(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        // This is the codon that is present if you ignore the insertions
        Triple<AminoAcid> codonWithoutInsertions = new Triple<>(
                AminoAcid.INVALID,
                AminoAcid.INVALID,
                AminoAcid.INVALID
        );

        Pair<AminoAcid> aminoAcidBeforeInsert = currentStep.getAminoAcidsBeforeInsert(HMMState.insertStateForMatching(toState));

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

        // If it's a stop codon, we can't be in an M' state, but should be in an END' state
        if (!isStopCodon(codonWithoutInsertions)) {
            return previous.getValueFor(HMMState.insertStateForMatching(toState)) *                     // Probability to be in state Ix at t-1
                    parameters.getInnerTransitionProbability(HMMInnerTransition.INSERT_MATCH) * 0.25;   // Probability for transmission + emission for I -> M
        }
        return 0;
    }

    /**
     * Test if the triple is a stop codon
     * @param tripleToCheck The codon to check
     * @return true if this can be a stop codon, false otherwise
     */
    protected abstract boolean isStopCodon(Triple<AminoAcid> tripleToCheck);
}

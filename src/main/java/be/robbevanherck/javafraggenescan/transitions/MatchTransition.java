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

        double bestValue = getProbabilityFromMatch(parameters, previous, codonEndingAtT);

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        bestValue = Math.max(bestValue, getProbabilityThroughDeletions(parameters, previous, codonEndingAtT));

        /* FROM I STATE */

        bestValue = Math.max(bestValue, getProbabilityFromInsertion(parameters, previous, currentStep));

        /* FROM START STATE */

        bestValue = Math.max(bestValue, getProbabilityFromStart(currentStep, previous, codonEndingAtT));

        return bestValue;
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

    /**
     * Calculate the probability to go from an M state to the target M state
     * @param parameters The HMM parameters
     * @param previous The previous step
     * @param codonEndingAtT The codon starting at t-2 and ending at t
     * @return The probability
     */
    // As long as there is no generified function to get emissions, this stays abstract
    protected abstract double getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT);

    /**
     * Calculate the probability of going from an M state, through some amount of D states to the destination M state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param codonEndingAtT The coding starting at t-2 to t
     * @return The probability
     */
    // As long as there is no generified function to get emissions, this stays abstract
    protected abstract  double getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT);

    /**
     * Calculate the probability of going from a Start state to the destination M state
     * @param currentStep The current Viterbi step
     * @param previous The previous Viterbi step
     * @param codonEndingAtT The coding starting at t-2 to t
     * @return The probability
     */
    // As long as there is no generified function to get emissions, this stays abstract
    protected abstract double getProbabilityFromStart(ViterbiStep currentStep, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT);
}

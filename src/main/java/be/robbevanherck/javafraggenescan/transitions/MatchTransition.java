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
    public MatchTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public PathProbability calculatePathProbability(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        /* FROM M STATE */

        PathProbability bestValue = getProbabilityFromMatch(parameters, previous, getCodonEndingAtT(currentStep));

        /* FROM M STATE, GOING THROUGH (numD) D STATES */

        bestValue = PathProbability.max(bestValue, getProbabilityThroughDeletions(parameters, previous, getCodonEndingAtT(currentStep)));

        /* FROM I STATE */

        bestValue = PathProbability.max(bestValue, getProbabilityFromInsertion(parameters, currentStep));

        /* FROM START STATE */

        bestValue = PathProbability.max(bestValue, getProbabilityFromStart(currentStep, getCodonEndingAtT(currentStep)));

        return bestValue;
    }

    /**
     * Calculate the probability of going from an I' state to the destination M' state
     * @param parameters The parameters for HMM
     * @param currentStep The current Viterbi step
     * @return The probability
     */
    protected PathProbability getProbabilityFromInsertion(HMMParameters parameters, ViterbiStep currentStep) {
       ViterbiStep previous = currentStep.getPrevious();

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

        HMMState insertionState = HMMState.insertStateForMatching(toState);

        // If it's a stop codon, we can't be in an M' state, but should be in an END' state
        if (!isCorrectStopCodon(codonWithoutInsertions)) {
            double probability = previous.getProbabilityFor(insertionState) *                           // Probability to be in state Ix at t-1
                    parameters.getInnerTransitionProbability(HMMInnerTransition.INSERT_MATCH) * 0.25;   // Probability for transmission + emission (=0.25) for I -> M
            return new PathProbability(insertionState, probability);
        }
        return new PathProbability(insertionState, 0);
    }

    /**
     * Test if the triple is the right (forward/reverse) stop codon
     * @param tripleToCheck The codon to check
     * @return true if this can be a stop codon, false otherwise
     */
    protected abstract boolean isCorrectStopCodon(Triple<AminoAcid> tripleToCheck);

    /**
     * Calculate the probability to go from an M state to the target M state
     * @param parameters The HMM parameters
     * @param previous The previous step
     * @param codonEndingAtT The codon starting at t-2 and ending at t
     * @return The probability
     */
    // As long as there is no generified function to get emissions, this stays abstract
    protected abstract PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT);

    /**
     * Calculate the probability of going from an M state, through some amount of D states to the destination M state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param codonEndingAtT The coding starting at t-2 to t
     * @return The probability
     */
    // As long as there is no generified function to get emissions, this stays abstract
    protected abstract PathProbability getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT);

    /**
     * Calculate the probability of going from a Start state to the destination M state
     * @param currentStep The current Viterbi step
     * @param codonEndingAtT The coding starting at t-2 to t
     * @return The probability
     */
    // As long as there is no generified function to get emissions, this stays abstract
    protected abstract PathProbability getProbabilityFromStart(ViterbiStep currentStep, Triple<AminoAcid> codonEndingAtT);
}

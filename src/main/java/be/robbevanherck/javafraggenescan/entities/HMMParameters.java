package be.robbevanherck.javafraggenescan.entities;

import java.util.Map;

/**
 * Contains all the parameters for the Hidden Markov Model
 */
public class HMMParameters {
    private final Map<HMMInnerTransition, Double> innerTransitions;
    private final Map<Triple<AminoAcid>, Map<HMMState, Double>> matchEmissions;
    private final boolean wholeGenome;

    /**
     * Create the parameters
     * @param matchEmissions The probabilities for an M state to emit, given a pair of 2
     * @param innerTransitions The probabilities for an inner transitions
     * @param wholeGenome Whether the input are whole genomes
     */
    public HMMParameters(Map<Triple<AminoAcid>, Map<HMMState, Double>> matchEmissions, Map<HMMInnerTransition, Double> innerTransitions, boolean wholeGenome) {
        this.matchEmissions = matchEmissions;
        this.innerTransitions = innerTransitions;
        this.wholeGenome = wholeGenome;
    }

    /**
     * Get the probability for an M state to emit its value
     * @param aminoAcidEndingInT The trinucleotide starting at t-2 and ending at t
     * @param state The state the transition is going to
     * @return The probability
     */
    public double getMatchEmissionFor(Triple<AminoAcid> aminoAcidEndingInT, HMMState state) {
        return matchEmissions.get(aminoAcidEndingInT).get(state);
    }

    /**
     * Get the probability of a inner state transition
     * @param transition The transition
     * @return The probability for that transition
     */
    public double getInnerTransitionProbability(HMMInnerTransition transition) {
        return innerTransitions.get(transition);
    }

    /**
     * Return if the input are whole genomes
     * @return true if whole genomes, false if parts
     */
    public boolean wholeGenome() {
        return wholeGenome;
    }
}

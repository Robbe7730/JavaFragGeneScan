package be.robbevanherck.javafraggenescan.entities;

import java.util.Map;

/**
 * Contains all the parameters for the Hidden Markov Model
 */
public class HMMParameters {
    private final Map<Triple<AminoAcid>, Map<HMMState, Float>> matchEmissions;

    /**
     * Create the parameters
     * @param matchEmissions The probabilities for an M state to emit, given a pair of 2
     */
    public HMMParameters(Map<Triple<AminoAcid>, Map<HMMState, Float>> matchEmissions) {
        this.matchEmissions = matchEmissions;
    }

    /**
     * Get the probability for an M state to emit its value
     * @param aminoAcidEndingInT The trinucleotide starting at t-2 and ending at t
     * @param state The state the transition is going to
     * @return The probability
     */
    public float getMatchEmissionFor(Triple<AminoAcid> aminoAcidEndingInT, HMMState state) {
        return matchEmissions.get(aminoAcidEndingInT).get(state);
    }
}

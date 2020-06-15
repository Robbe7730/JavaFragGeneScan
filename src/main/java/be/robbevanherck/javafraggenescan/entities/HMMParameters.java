package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.repositories.MatchEmissionRepository;
import be.robbevanherck.javafraggenescan.repositories.InputFileRepository;

import java.io.File;
import java.util.Map;

/**
 * Contains all the parameters for the Hidden Markov Model
 */
public class HMMParameters {
    private final Map<HMMInnerTransition, Double> innerTransitions;
    private final Map<HMMOuterTransition, Double> outerTransitions;
    private final Map<HMMState, Map<Triple<AminoAcid>, Double>> matchEmissions;
    private final Map<Pair<AminoAcid>, Double> insertInsertEmissions;
    private final Map<Pair<AminoAcid>, Double> matchInsertEmissions;
    private final boolean wholeGenome;

    /**
     * Create the parameters
     * @param countGC The percentage of G/C amino-acids in the input compared to the length of the input
     * @param wholeGenome Whether the input are whole genomes
     */
    public HMMParameters(int countGC, boolean wholeGenome) {
        this.matchEmissions = MatchEmissionRepository.getMatchEmissions(countGC);
        this.innerTransitions = InputFileRepository.getInnerTransitions();
        this.outerTransitions = InputFileRepository.getOuterTransitions();
        this.insertInsertEmissions = InputFileRepository.getInsertInsertEmissions();
        this.matchInsertEmissions = InputFileRepository.getMatchInsertEmissions();
        this.wholeGenome = wholeGenome;
    }

    /**
     * Setup all the repositories, should only be called once as this reads a lot of files
     * @param inputFile The file given as a command-line argument
     */
    public static void setup(File inputFile) {
        MatchEmissionRepository.setup();
        InputFileRepository.setup(inputFile);
    }

    /**
     * Return if the input are whole genomes
     * @return true if whole genomes, false if parts
     */
    public boolean wholeGenome() {
        return wholeGenome;
    }

    /**
     * Get the probability for an M state to emit its value
     * @param aminoAcidEndingInT The trinucleotide starting at t-2 and ending at t
     * @param state The state the transition is going to
     * @return The probability
     */
    public double getMatchEmissionProbability(HMMState state, Triple<AminoAcid> aminoAcidEndingInT) {
        return matchEmissions.get(state).get(aminoAcidEndingInT);
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
     * Get the probability of a outer state transition
     * @param transition The transition
     * @return The probability for that transition
     */
    public double getOuterTransitionProbability(HMMOuterTransition transition) {
        return outerTransitions.get(transition);
    }

    /**
     * Get the probability a transition I -> I to emit its value
     * @param previousInput The input of the previous state
     * @param currentInput The input of the current state
     * @return The probability
     */
    public double getInsertInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        return insertInsertEmissions.get(new Pair<>(previousInput, currentInput));
    }
    /**
     * Get the probability a transition M -> I to emit its value
     * @param previousInput The input of the previous state
     * @param currentInput The input of the current state
     * @return The probability
     */
    public double getMatchInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        return matchInsertEmissions.get(new Pair<>(previousInput, currentInput));
    }
}

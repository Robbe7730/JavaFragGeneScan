package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.repositories.*;

import java.io.File;
import java.util.Map;

/**
 * Contains all the parameters for the Hidden Markov Model
 */
public class HMMParameters {
    private final Map<HMMInnerTransition, Double> innerTransitions;
    private final Map<HMMOuterTransition, Double> outerTransitions;
    private final Map<HMMState, Map<Triple<AminoAcid>, Double>> forwardMatchEmissions;
    private final Map<HMMState, Map<Triple<AminoAcid>, Double>> reverseMatchEmissions;
    private final Map<Pair<AminoAcid>, Double> insertInsertEmissions;
    private final Map<Pair<AminoAcid>, Double> matchInsertEmissions;
    private final Map<Pair<AminoAcid>, Double> nonCodingNonCodingEmissions;
    private final boolean wholeGenome;
    private final Map<Integer, Map<Triple<AminoAcid>, Double>> forwardStopPWM;
    private final Map<Integer, Map<Triple<AminoAcid>, Double>> reverseStopPWM;
    private final Map<Integer, Map<Triple<AminoAcid>, Double>> forwardStartPWM;
    private final Map<Integer, Map<Triple<AminoAcid>, Double>> reverseStartPWM;
    private final Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, Double>> gaussianArguments;

    /**
     * Protected constructor only for testing
     */
    protected HMMParameters() {
        this.forwardMatchEmissions = null;
        this.reverseMatchEmissions = null;

        this.nonCodingNonCodingEmissions = null;

        this.forwardStopPWM = null;
        this.reverseStopPWM = null;
        this.forwardStartPWM = null;
        this.reverseStartPWM = null;

        this.gaussianArguments = null;

        this.innerTransitions = null;
        this.outerTransitions = null;
        this.insertInsertEmissions = null;
        this.matchInsertEmissions = null;
        this.wholeGenome = false;
    }

    /**
     * Create the parameters
     * @param countGC The percentage of G/C amino-acids in the input compared to the length of the input
     * @param wholeGenome Whether the input are whole genomes
     */
    public HMMParameters(int countGC, boolean wholeGenome) {
        this.forwardMatchEmissions = ForwardMatchEmissionRepository.getInstance().getValues(countGC);
        this.reverseMatchEmissions = ReverseMatchEmissionRepository.getInstance().getValues(countGC);

        this.nonCodingNonCodingEmissions = NonCodingEmissionRepository.getInstance().getValues(countGC);

        this.forwardStopPWM = ForwardEndRepository.getInstance().getValues(countGC);
        this.reverseStopPWM = ReverseEndRepository.getInstance().getValues(countGC);
        this.forwardStartPWM = ForwardStartRepository.getInstance().getValues(countGC);
        this.reverseStartPWM = ReverseStartRepository.getInstance().getValues(countGC);

        this.gaussianArguments = GaussianArgumentsRepository.getInstance().getValues(countGC);

        this.innerTransitions = InputFileRepository.getInstance().getInnerTransitions();
        this.outerTransitions = InputFileRepository.getInstance().getOuterTransitions();
        this.insertInsertEmissions = InputFileRepository.getInstance().getInsertInsertEmissions();
        this.matchInsertEmissions = InputFileRepository.getInstance().getMatchInsertEmissions();
        this.wholeGenome = wholeGenome;
    }

    /**
     * Setup all the repositories, should only be called once as this reads a lot of files
     * @param inputFile The file given as a command-line argument
     */
    public static void setup(File inputFile) {
        ForwardMatchEmissionRepository.createInstance();
        ReverseMatchEmissionRepository.createInstance();

        NonCodingEmissionRepository.createInstance();

        ForwardEndRepository.createInstance();
        ReverseEndRepository.createInstance();
        ForwardStartRepository.createInstance();
        ReverseStartRepository.createInstance();

        GaussianArgumentsRepository.createInstance();

        InputFileRepository.createInstance(inputFile);
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
        //TODO: check what to do with invalid acids...
        if (aminoAcidEndingInT.contains(AminoAcid.INVALID)) {
            return 0;
        }
        return this.forwardMatchEmissions.get(state).get(aminoAcidEndingInT);
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
        if (currentInput == AminoAcid.INVALID || previousInput == AminoAcid.INVALID) {
            return 0;
        }
        return insertInsertEmissions.get(new Pair<>(previousInput, currentInput));
    }

    /**
     * Get the probability a transition M -> I to emit its value
     * @param previousInput The input of the previous state
     * @param currentInput The input of the current state
     * @return The probability
     */
    public double getMatchInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        if (currentInput == AminoAcid.INVALID|| previousInput == AminoAcid.INVALID) {
            return 0;
        }
        return matchInsertEmissions.get(new Pair<>(previousInput, currentInput));
    }


    /**
     * Get the probability a transition R -> R to emit its value
     * @param previousInput The input of the previous state
     * @param currentInput The input of the current state
     * @return The probability
     */
    public double getNonCodingNonCodingEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        if (currentInput == AminoAcid.INVALID|| previousInput == AminoAcid.INVALID) {
            return 0;
        }
        return nonCodingNonCodingEmissions.get(new Pair<>(previousInput, currentInput));
    }


    /**
     * Get the probability for an M' state to emit its value
     * @param aminoAcidEndingInT The trinucleotide starting at t-2 and ending at t
     * @param state The state the transition is going to
     * @return The probability
     */
    public double getReverseMatchEmissionProbability(HMMState state, Triple<AminoAcid> aminoAcidEndingInT) {
        if (aminoAcidEndingInT.contains(AminoAcid.INVALID)) {
            return 0;
        }
        return reverseMatchEmissions.get(state).get(aminoAcidEndingInT);
    }

    /**
     * Get the probability from the PWM for the Forward Stop codons
     * @param index The index in the frame
     * @param codon The codon at the position index
     * @return The probability
     */
    public double getForwardEndPWMProbability(int index, Triple<AminoAcid> codon) {
        if (codon.contains(AminoAcid.INVALID)) {
            return 0;
        }
        return forwardStopPWM.get(index).get(codon);
    }

    /**
     * Get the probability from the PWM for the Reverse Stop codons
     * @param index The index in the frame
     * @param codon The codon at the position index
     * @return The probability
     */
    public double getReverseEndPWMProbability(int index, Triple<AminoAcid> codon) {
        if (codon.contains(AminoAcid.INVALID)) {
            return 0;
        }
        return reverseStopPWM.get(index).get(codon);
    }

    /**
     * Get the probability from the PWM for the Forward Start codons
     * @param index The index in the frame
     * @param codon The codon at the position index
     * @return The probability
     */
    public double getForwardStartPWMProbability(int index, Triple<AminoAcid> codon) {
        if (codon.contains(AminoAcid.INVALID)) {
            return 0;
        }
        return forwardStartPWM.get(index).get(codon);
    }

    /**
     * Get the probability from the PWM for the Reverse Start codons
     * @param index The index in the frame
     * @param codon The codon at the position index
     * @return The probability
     */
    public double getReverseStartPWMProbability(int index, Triple<AminoAcid> codon) {
        if (codon.contains(AminoAcid.INVALID)) {
            return 0;
        }
        return reverseStartPWM.get(index).get(codon);
    }

    /**
     * Get the argument for the Gaussian curves for detecting start/stop codons
     * @param state Either START, STOP, START_REVERSE or END_REVERSE
     * @param argument The argument to get
     * @return The value
     */
    public double getGaussianArgument(HMMState state, GaussianArgumentsRepository.GaussianArgument argument) {
        return gaussianArguments.get(state).get(argument);
    }
}

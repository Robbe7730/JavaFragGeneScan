package be.robbevanherck.javafraggenescan.entities;

import java.util.EnumMap;

/**
 * Represents (the result of) a single step of the Viterbi algorithm
 */
public class ViterbiStep {
    private final EnumMap<HMMState, Float> probabilities;
    private final EnumMap<HMMState, HMMState> paths;
    private final AminoAcid input;
    private final ViterbiStep previous;

    /**
     * Create a ViterbiStep with no values
     * @param input The input for this step
     * @param previous The previous step
     */
    public ViterbiStep(AminoAcid input, ViterbiStep previous) {
        this.input = input;
        this.previous = previous;

        probabilities = new EnumMap<>(HMMState.class);
        paths = new EnumMap<>(HMMState.class);
    }

    /**
     * Get the probability of being in this specific state at this time
     * @param state The state to look up
     * @return The probability to be in this state
     */
    public float getValueFor(HMMState state) {
        return probabilities.get(state);
    }

    /**
     * Set the probability of being in this specific state at this time
     * @param state The state of which to modify the probability
     * @param value The new probability
     */
    public void setValueFor(HMMState state, float value) {
        probabilities.put(state, value);
    }

    /**
     * Get the previous state for the given state
     * @param state The state to look up
     * @return The previous state for the given state
     */
    public HMMState getBacktrackPathFor(HMMState state) {
        return paths.get(state);
    }

    /**
     * Set the previous state for the given state
     * @param state The state to modify
     * @param previousState The new previous state
     */
    public void getBacktrackPathFor(HMMState state, HMMState previousState) {
        paths.put(state, previousState);
    }

    /**
     * Get the input for this step
     * @return The input
     */
    public AminoAcid getInput() {
        return input;
    }

    /**
     * Get the previous step
     * @return The previous step
     */
    public ViterbiStep getPrevious() {
        return previous;
    }
}

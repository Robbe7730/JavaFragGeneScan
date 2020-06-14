package be.robbevanherck.javafraggenescan.entity;

import be.robbevanherck.javafraggenescan.enums.StateEnum;

import java.util.EnumMap;

/**
 * Represents (the result of) a single step of the Viterbi algorithm
 */
public class ViterbiStep {
    private final EnumMap<StateEnum, Float> probabilities;
    private final EnumMap<StateEnum, StateEnum> paths;

    /**
     * Create a ViterbiStep with no values
     */
    public ViterbiStep() {
        probabilities = new EnumMap<>(StateEnum.class);
        paths = new EnumMap<>(StateEnum.class);
    }

    /**
     * Get the probability of being in this specific state at this time
     * @param state The state to look up
     * @return The probability to be in this state
     */
    public float getValueFor(StateEnum state) {
        return probabilities.get(state);
    }

    /**
     * Set the probability of being in this specific state at this time
     * @param state The state of which to modify the probability
     * @param value The new probability
     */
    public void setValueFor(StateEnum state, float value) {
        probabilities.put(state, value);
    }

    /**
     * Get the previous state for the given state
     * @param state The state to look up
     * @return The previous state for the given state
     */
    public StateEnum getPreviousStateFor(StateEnum state) {
        return paths.get(state);
    }

    /**
     * Set the previous state for the given state
     * @param state The state to modify
     * @param previousState The new previous state
     */
    public void setPreviousStateFor(StateEnum state, StateEnum previousState) {
        paths.put(state, previousState);
    }
}

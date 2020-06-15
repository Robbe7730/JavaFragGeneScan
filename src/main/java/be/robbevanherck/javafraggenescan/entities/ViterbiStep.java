package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.TransitionRepository;
import be.robbevanherck.javafraggenescan.transitions.Transition;

import java.util.*;

/**
 * Represents (the result and context of) a single step of the Viterbi algorithm
 */
public class ViterbiStep {
    private final EnumMap<HMMState, Float> probabilities;
    private final EnumMap<HMMState, HMMState> paths;
    private final AminoAcid input;
    private final ViterbiStep previous;
    private final HMMParameters parameters;


    /**
     * Keep track of the disabled states, for example if a stop codon is matched at time t, there is no possibility
     * for an M1/M4 state at time t, nor M2/M5 at t+1, nor M3/M6 at t+2
     */
    private Set<HMMState> disabledStates;

    /**
     * The progression of disabled states, a disabled state of M1 will for example always lead to a disabled state of
     * M2 in the next step. If a state is not a key of this map, that means that there is no next disabled state.
     */
    private final Map<HMMState, HMMState> disabledStateProgression = Map.of(
            HMMState.MATCH_1, HMMState.MATCH_2,
            HMMState.MATCH_2, HMMState.MATCH_3,
            HMMState.MATCH_4, HMMState.MATCH_5,
            HMMState.MATCH_5, HMMState.MATCH_6
    );

    /**
     * Create a ViterbiStep with a previous step
     * @param parameters The parameters for the HMM
     * @param input The input for this step
     * @param previous The previous step
     */
    public ViterbiStep(HMMParameters parameters, AminoAcid input, ViterbiStep previous) {
        this.input = input;
        this.previous = previous;
        this.parameters = parameters;
        this.disabledStates = new HashSet<>();

        probabilities = new EnumMap<>(HMMState.class);
        paths = new EnumMap<>(HMMState.class);
    }

    /**
     * Create a ViterbiStep without a previous step
     * @param parameters The parameters for the HMM
     * @param input The input for this step
     */
    public ViterbiStep(HMMParameters parameters, AminoAcid input) {
        this(parameters, input, null);
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

    /**
     * Set which states are disabled in this states
     * @param disabledStates Set of disabled states
     */
    public void setDisabledStates(Set<HMMState> disabledStates) {
        this.disabledStates = disabledStates;
    }

    /**
     * Calculate the next state and return that
     * @param input The input for the step
     * @return The new step
     */
    public ViterbiStep calculateNext(AminoAcid input) {
        ViterbiStep ret =  new ViterbiStep(this.parameters, input, this);

        // Make sure the disabled states are propagated
        if (!disabledStates.isEmpty()) {
            Set<HMMState> newDisabledStates = new HashSet<>();
            for (HMMState disabledState : disabledStates) {
                if (disabledStateProgression.containsKey(disabledState)) {
                    newDisabledStates.add(disabledStateProgression.get(disabledState));
                }
            }
            ret.setDisabledStates(newDisabledStates);
        }

        // Calculate the values
        for (Transition transition : TransitionRepository.getInstance().getTransitions()) {
            if (!disabledStates.contains(transition.getToState())) {
                transition.calculateStateTransition(ret);
            } else {
                ret.setValueFor(transition.getToState(), 0);
            }
        }

        return ret;
    }

    public HMMParameters getParameters() {
        return parameters;
    }
}

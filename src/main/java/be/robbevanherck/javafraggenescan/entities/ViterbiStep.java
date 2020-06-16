package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.ViterbiAlgorithm;
import be.robbevanherck.javafraggenescan.repositories.InputFileRepository;
import be.robbevanherck.javafraggenescan.transitions.Transition;

import java.util.*;

/**
 * Represents (the result and context of) a single step of the Viterbi algorithm
 */
public class ViterbiStep {
    private final EnumMap<HMMState, Double> probabilities;
    private final EnumMap<HMMState, HMMState> paths; // TODO
    private final ViterbiStep previous;
    private final HMMParameters parameters;
    private final List<AminoAcid> nextValues;

    /**
     * The aminoAcids that came right before the given insertion, used to verify that we aren't in a stop state when calculating I -> M
     */
    private final Map<HMMState, Pair<AminoAcid>> aminoAcidsBeforeInsertion;

    /**
     * Keep track of the overridden values, for example if a stop codon is matched at time t, there is no possibility
     * for an M1/M4 state at time t, nor M2/M5 at t+1, nor M3/M6 at t+2, so these values have to be 0
     */
    private final Map<Integer, Map<HMMState, Double>> overriddenValues;

    /**
     * Create a ViterbiStep with a previous step
     * @param parameters The parameters for the HMM
     * @param previous The previous step
     * @param overriddenValues The values that are overridden
     * @param nextValues The next values in the sequence
     */
    public ViterbiStep(HMMParameters parameters, ViterbiStep previous, Map<Integer, Map<HMMState, Double>> overriddenValues, List<AminoAcid> nextValues) {
        this.parameters = parameters;
        this.previous = previous;
        this.aminoAcidsBeforeInsertion = new EnumMap<>(HMMState.class);
        this.overriddenValues = overriddenValues;
        this.nextValues = nextValues;

        probabilities = new EnumMap<>(HMMState.class);
        paths = new EnumMap<>(HMMState.class);
    }

    /**
     * Create the initial ViterbiStep
     * @param parameters The parameters for the HMM
     * @param inputs The inputs starting from (including) this step
     */
    public ViterbiStep(HMMParameters parameters, List<AminoAcid> inputs) {
        this(parameters, null, new HashMap<>(), inputs);

        // Fill in the initial values
        for(Map.Entry<HMMState, Double> entry : InputFileRepository.getInstance().getInitialProbabilities().entrySet()) {
            this.setValueFor(entry.getKey(), entry.getValue());
        }

        //TODO check for start and stop state
    }

    /**
     * Get the probability of being in this specific state at this time
     * @param state The state to look up
     * @return The probability to be in this state
     */
    public double getValueFor(HMMState state) {
        return probabilities.get(state);
    }

    /**
     * Set the probability of being in this specific state at this time
     * @param state The state of which to modify the probability
     * @param value The new probability
     */
    public void setValueFor(HMMState state, double value) {
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
        return nextValues.get(0);
    }

    /**
     * Get the previous step
     * @return The previous step
     */
    public ViterbiStep getPrevious() {
        return previous;
    }

    /**
     * Set a value at t+delay
     * @param state The HMMState of which to set the value
     * @param value The value
     * @param delay The delay when to set the value
     */
    public void setValueFor(HMMState state, double value, int delay) {
        if (delay == 0) {
            this.setValueFor(state, value);
        } else {
            Map<HMMState, Double> values = this.overriddenValues.get(delay);

            if (values == null) {
                values = new EnumMap<>(HMMState.class);
            }

            values.put(state, value);

            this.overriddenValues.put(delay, values);
        }
    }

    /**
     * Calculate the next state and return that
     * @param input The input for the step
     * @param nextInput The input for the step after that
     * @return The new step
     */
    public ViterbiStep calculateNext(AminoAcid input, AminoAcid nextInput) {

        Map<Integer, Map<HMMState, Double>> newOverriddenValues = new HashMap<>();
        Map<HMMState, Double> currentOverriddenValues = new EnumMap<>(HMMState.class);

        // Make sure the disabled states are propagated
        for (Map.Entry<Integer, Map<HMMState, Double>> entry : this.overriddenValues.entrySet()) {
            if (entry.getKey() == 0) {
                currentOverriddenValues = entry.getValue();
            } else {
                newOverriddenValues.put(entry.getKey() - 1, entry.getValue());
            }
        }

        // Get the remaining values
        LinkedList<AminoAcid> newNextValues = new LinkedList<>(nextValues);
        newNextValues.remove();

        // Create the step
        ViterbiStep ret =  new ViterbiStep(this.parameters, this, newOverriddenValues, newNextValues);

        // Calculate the values
        for (Transition transition : ViterbiAlgorithm.TRANSITIONS) {
            if (!currentOverriddenValues.containsKey(transition.getToState())) {
                transition.calculateStateTransition(ret);
            } else {
                ret.setValueFor(transition.getToState(), currentOverriddenValues.get(transition.getToState()));
            }
        }

        return ret;
    }

    /**
     * Get the HMM parameters
     * @return The parameters
     */
    public HMMParameters getParameters() {
        return parameters;
    }

    /**
     * Get the pair of amino-acids before the given insertion
     * @param state The insertion state to lookup what came before
     * @return The pair of amino-acids
     */
    public Pair<AminoAcid> getAminoAcidsBeforeInsert(HMMState state) {
        return aminoAcidsBeforeInsertion.get(state);
    }
    /**
     * Set the pair of amino-acids before the given insertion
     * @param state The insertion state to set what came before
     * @param aminoAcids The amino-acids that came before the insertion
     */
    public void setAminoAcidsBeforeInsert(HMMState state, Pair<AminoAcid> aminoAcids) {
        aminoAcidsBeforeInsertion.put(state, aminoAcids);
    }

    public AminoAcid getNextInput() {
        return nextValues.get(1);
    }

    public List<AminoAcid> getNextValues() {
        return nextValues;
    }
}

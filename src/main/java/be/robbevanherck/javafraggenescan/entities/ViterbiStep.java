package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.ViterbiAlgorithm;
import be.robbevanherck.javafraggenescan.repositories.InputFileRepository;
import be.robbevanherck.javafraggenescan.transitions.Transition;

import java.util.*;

/**
 * Represents (the result and context of) a single step of the Viterbi algorithm
 */
public class ViterbiStep {
    private final EnumMap<HMMState, PathProbability> pathProbabilities;
    private final ViterbiStep previous;
    private final HMMParameters parameters;
    private final List<AminoAcid> nextValues;
    private boolean isInitialStep = false;

    /**
     * The aminoAcids that came right before the given insertion, used to verify that we aren't in a stop state when calculating I -> M
     */
    private final Map<HMMState, Pair<AminoAcid>> aminoAcidsBeforeInsertion;

    /**
     * Keep track of the overridden values, for example if a stop codon is matched at time t, there is no possibility
     * for an M1/M4 state at time t, nor M2/M5 at t+1, nor M3/M6 at t+2, so these values have to be 0
     */
    private final Map<Integer, Map<HMMState, PathProbability>> overriddenValues;

    /**
     * Empty constructor used only for testing
     */
    protected ViterbiStep() {
        this.parameters = null;
        this.previous = null;
        this.aminoAcidsBeforeInsertion = new EnumMap<>(HMMState.class);
        this.overriddenValues = null;
        this.nextValues = null;

        pathProbabilities = new EnumMap<>(HMMState.class);
    }

    /**
     * Create a ViterbiStep with a previous step
     * @param parameters The parameters for the HMM
     * @param previous The previous step
     * @param overriddenValues The values that are overridden
     * @param nextValues The next values in the sequence
     */
    public ViterbiStep(HMMParameters parameters, ViterbiStep previous, Map<Integer, Map<HMMState, PathProbability>> overriddenValues, List<AminoAcid> nextValues) {
        this.parameters = parameters;
        this.previous = previous;
        this.aminoAcidsBeforeInsertion = new EnumMap<>(HMMState.class);
        this.overriddenValues = overriddenValues;
        this.nextValues = nextValues;

        pathProbabilities = new EnumMap<>(HMMState.class);
    }

    /**
     * Create the initial ViterbiStep
     * @param parameters The parameters for the HMM
     * @param firstInput The input for this ViterbiStep
     * @param nextInputs The inputs starting from (including) this step
     */
    public ViterbiStep(HMMParameters parameters, AminoAcid firstInput, List<AminoAcid> nextInputs) {
        this(parameters, null, new HashMap<>(), nextInputs);

        this.isInitialStep = true;

        // Fill in the initial values
        for(Map.Entry<HMMState, PathProbability> entry : InputFileRepository.getInstance().getInitialProbabilities().entrySet()) {
            this.setValueFor(entry.getKey(), entry.getValue());
        }

        Triple<AminoAcid> firstCodon;
        if (nextInputs.size() > 1) {
            firstCodon = new Triple<>(firstInput, nextInputs.get(0), nextInputs.get(1));
        } else {
            firstCodon = new Triple<>(AminoAcid.INVALID, AminoAcid.INVALID, AminoAcid.INVALID);
        }

        if (StartStopUtil.isForwardStopCodon(firstCodon)) {
            // Depending on which codon it is, we calculate a new probability for the END state
            // The first value is always T, so we don't need to check that one
            double probability = 0;
            if (firstCodon.getSecondValue() == AminoAcid.A && firstCodon.getThirdValue() == AminoAcid.A) {
                probability = 0.53;
            } else if (firstCodon.getSecondValue() == AminoAcid.A && firstCodon.getThirdValue() == AminoAcid.G) {
                probability = 0.16;
            } else if (firstCodon.getSecondValue() == AminoAcid.G && firstCodon.getThirdValue() == AminoAcid.A) {
                probability = 0.30;
            }

            this.setValueFor(HMMState.END, new PathProbability(HMMState.NO_STATE, 0));
            this.setValueFor(HMMState.END, new PathProbability(HMMState.END, 0), 1);
            this.setValueFor(HMMState.END, new PathProbability(HMMState.END, probability), 2);

            this.setValueFor(HMMState.MATCH_6, new PathProbability(HMMState.NO_STATE, 0), 2);
            this.setValueFor(HMMState.MATCH_5, new PathProbability(HMMState.NO_STATE, 0), 1);
            this.setValueFor(HMMState.MATCH_4, new PathProbability(HMMState.NO_STATE, 0));
            this.setValueFor(HMMState.MATCH_3, new PathProbability(HMMState.NO_STATE, 0), 2);
            this.setValueFor(HMMState.MATCH_2, new PathProbability(HMMState.NO_STATE, 0), 1);
            this.setValueFor(HMMState.MATCH_1, new PathProbability(HMMState.NO_STATE, 0));
        } else if (StartStopUtil.isReverseStartCodon(firstCodon)) {
            // Depending on which codon it is, we calculate a new probability for the START' state
            // The last value is always A, so we don't need to check that one
            double probability = this.getPathProbabilityFor(HMMState.START).getProbability();
            if (firstCodon.getFirstValue() == AminoAcid.T && firstCodon.getSecondValue() == AminoAcid.T) {
                probability *= 0.53;
            } else if (firstCodon.getFirstValue() == AminoAcid.C && firstCodon.getSecondValue() == AminoAcid.T) {
                probability *= 0.16;
            } else if (firstCodon.getFirstValue() == AminoAcid.T && firstCodon.getSecondValue() == AminoAcid.C) {
                probability *= 0.30;
            }

            this.setValueFor(HMMState.START_REVERSE, new PathProbability(HMMState.NO_STATE, 0));
            this.setValueFor(HMMState.START_REVERSE, new PathProbability(HMMState.START_REVERSE, 0), 1);
            this.setValueFor(HMMState.START_REVERSE, new PathProbability(HMMState.START_REVERSE, probability), 2);

            this.setValueFor(HMMState.MATCH_REVERSE_3, new PathProbability(HMMState.NO_STATE, 0), 2);
            this.setValueFor(HMMState.MATCH_REVERSE_6, new PathProbability(HMMState.NO_STATE, 0), 2);
        }
    }

    /**
     * Get the probability of being in this specific state at this time
     * @param state The state to look up
     * @return The probability to be in this state
     */
    public double getProbabilityFor(HMMState state) {
        return pathProbabilities.get(state).getProbability();
    }

    /**
     * Set the probability of being in this specific state at this time
     * @param state The state of which to modify the probability
     * @param value The new probability
     */
    public void setValueFor(HMMState state, PathProbability value) {
        pathProbabilities.put(state, value);
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
    public void setValueFor(HMMState state, PathProbability value, int delay) {
        if (delay == 0) {
            this.setValueFor(state, value);
        } else {
            Map<HMMState, PathProbability> values = this.overriddenValues.get(delay);

            if (values == null) {
                values = new EnumMap<>(HMMState.class);
            }

            values.put(state, value);

            this.overriddenValues.put(delay, values);
        }
    }

    /**
     * Calculate the next state and return that
     * @return The new step
     */
    public ViterbiStep calculateNext() {

        Map<Integer, Map<HMMState, PathProbability>> newOverriddenValues = new HashMap<>();
        Map<HMMState, PathProbability> currentOverriddenValues = new EnumMap<>(HMMState.class);

        // Make sure the disabled states are propagated
        for (Map.Entry<Integer, Map<HMMState, PathProbability>> entry : this.overriddenValues.entrySet()) {
            if (entry.getKey() <= 1) {
                currentOverriddenValues = entry.getValue();
            } else {
                newOverriddenValues.put(entry.getKey() - 1, entry.getValue());
            }
        }

        // Get the remaining values
        LinkedList<AminoAcid> newNextValues = new LinkedList<>(nextValues);

        // The initial step shouldn't remove the first value
        if (!isInitialStep) {
            newNextValues.remove();
        }

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
        if (nextValues.size() > 1) {
            return nextValues.get(1);
        } else {
            return AminoAcid.INVALID;
        }
    }

    public List<AminoAcid> getNextValues() {
        return nextValues;
    }

    /**
     * Print out the step
     */
    public void print() {
        for (HMMState state : HMMState.values()) {
            if (state != HMMState.NO_STATE) {
                System.out.print(pathProbabilities.get(state).getPreviousState());
                System.out.print("\t");
            }
        }
        System.out.println();
        for (HMMState state : HMMState.values()) {
            if (state != HMMState.NO_STATE) {
                System.out.print(pathProbabilities.get(state).getProbability());
                System.out.print("\t");
            }
        }
        System.out.println();
    }

    public HMMState getHighestProbabilityState() {
        HMMState ret = HMMState.NO_STATE;
        double currentProbability = 0;
        for (HMMState state : HMMState.values()) {
            if (state != HMMState.NO_STATE) {
                PathProbability pathProbability = pathProbabilities.get(state);
                if (pathProbability.getProbability() > currentProbability) {
                    currentProbability = pathProbability.getProbability();
                    ret = state;
                }
            }
        }
        return ret;
    }

    /**
     * Get the PathProbility for a state
     * @param state The state to look up
     * @return The PathProbability
     */
    public PathProbability getPathProbabilityFor(HMMState state) {
        return pathProbabilities.get(state);
    }
}

package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import be.robbevanherck.javafraggenescan.transitions.Transition;

import java.util.LinkedList;
import java.util.List;

/**
 * Main class for everything related to the Viterbi algorithm
 */
public class ViterbiAlgorithm {
    List<ViterbiStep> steps;
    ViterbiStep currStep;
    HMMParameters parameters;

    /**
     * Create a new ViterbiAlgorithm
     * @param parameters The parameters for the HMM
     */
    public ViterbiAlgorithm(HMMParameters parameters) {
        steps = new LinkedList<>();
        this.parameters = parameters;
    }

    /**
     * Run the entire algorithm, clears the state when completed
     * @param input The input for the algorithm
     * @return A list of states which can be used for backtracking
     */
    public List<ViterbiStep> run(AminoAcid[] input) {
        for (AminoAcid c : input) {
            nextStep(c);
        }
        steps.add(currStep);
        return steps;
    }

    /**
     * Run one step of the Viterbi algorithm
     * @param input Input for the step
     */
    private void nextStep(AminoAcid input) {
        if (steps.isEmpty()) {
            currStep = initialStep(input);
        } else {
            ViterbiStep nextStep = new ViterbiStep(input, currStep);
            for (Transition transition : TransitionRepository.getInstance().getTransitions()) {
                transition.calculateStateTransition(parameters, nextStep);
            }
            steps.add(currStep);
            currStep = nextStep;
        }
    }

    /**
     * Returns the initial setup of the Viterbi algorithm
     * @param input Input for the step
     * @return The initial state
     */
    private ViterbiStep initialStep(AminoAcid input) {
        return new ViterbiStep(input, null);
    }
}

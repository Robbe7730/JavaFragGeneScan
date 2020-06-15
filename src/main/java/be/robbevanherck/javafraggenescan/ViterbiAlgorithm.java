package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

import java.util.List;

/**
 * Main class for everything related to the Viterbi algorithm
 */
public class ViterbiAlgorithm {
    HMMParameters parameters;

    /**
     * Create a new ViterbiAlgorithm
     * @param parameters The parameters for the HMM
     */
    public ViterbiAlgorithm(HMMParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Run the entire algorithm, clears the state when completed
     * @param input The input for the algorithm
     * @return The last step, which can be used for backtracking
     */
    public ViterbiStep run(List<AminoAcid> input) {

        ViterbiStep currentStep = new ViterbiStep(parameters, input.get(0), input.get(1));
        for (int i = 1; i < input.size() - 1; i++) {
            currentStep = currentStep.calculateNext(input.get(i), input.get(i+1));
        }
        currentStep = currentStep.calculateNext(input.get(input.size() - 1), null);
        return currentStep;
    }
}

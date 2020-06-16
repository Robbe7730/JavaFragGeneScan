package be.robbevanherck.javafraggenescan.entities;

import java.util.List;

/**
 * Represents the input for the Viterbi algorithm
 */
public class ViterbiAlgorithmInput {
    private final List<AminoAcid> inputAcids;
    private final String name;

    /**
     * Create a new ViterbiAlgorithmInput
     * @param name The name of the input
     * @param inputAcids The amino-acids
     */
    public ViterbiAlgorithmInput(String name, List<AminoAcid> inputAcids) {
        this.name = name;
        this.inputAcids = inputAcids;
    }

    public List<AminoAcid> getInputAcids() {
        return inputAcids;
    }

    public String getName() {
        return name;
    }
}

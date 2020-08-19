package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.*;

public class DummyHMMParameters extends HMMParameters {
    /**
     * Create the dummy parameters
     */
    public DummyHMMParameters() {
        super();
    }

    @Override
    public double getInnerTransitionProbability(HMMInnerTransition transition) {
        return 0.1;
    }

    @Override
    public double getInsertInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        return 0.2;
    }

    @Override
    public double getMatchInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        return 0.3;
    }

    @Override
    public double getOuterTransitionProbability(HMMOuterTransition transition) {
        return 0.4;
    }

    @Override
    public double getMatchEmissionProbability(HMMState state, Triple<AminoAcid> aminoAcidEndingInT) {
        return 0.5;
    }

    @Override
    public boolean wholeGenome() {
        return false;
    }
}

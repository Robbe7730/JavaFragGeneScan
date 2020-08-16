package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.*;

import java.math.BigDecimal;

public class DummyHMMParameters extends HMMParameters {
    /**
     * Create the dummy parameters
     */
    public DummyHMMParameters() {
        super();
    }

    @Override
    public BigDecimal getInnerTransitionProbability(HMMInnerTransition transition) {
        return BigDecimal.valueOf(0.1);
    }

    @Override
    public BigDecimal getInsertInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        return BigDecimal.valueOf(0.2);
    }

    @Override
    public BigDecimal getMatchInsertEmissionProbability(AminoAcid previousInput, AminoAcid currentInput) {
        return BigDecimal.valueOf(0.3);
    }

    @Override
    public BigDecimal getOuterTransitionProbability(HMMOuterTransition transition) {
        //TODO: make this dependent on the input
        return BigDecimal.valueOf(0.4);
    }

    @Override
    public BigDecimal getMatchEmissionProbability(HMMState state, Triple<AminoAcid> aminoAcidEndingInT) {
        //TODO: make this dependent on the input
        return BigDecimal.valueOf(0.5);
    }

    @Override
    public boolean wholeGenome() {
        return false;
    }
}

package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DummyAcidsViterbiStep extends ViterbiStep {
    public AminoAcid input;
    public AminoAcid prev;
    public AminoAcid prePrev;
    public List<AminoAcid> nextValues;

    public DummyAcidsViterbiStep(AminoAcid input, AminoAcid previous, AminoAcid prePrevious, List<AminoAcid> nextValues) {
        super();
        this.input = input;
        this.prev = previous;
        this.prePrev = prePrevious;
        this.nextValues = nextValues;
    }

    public DummyAcidsViterbiStep(AminoAcid input, AminoAcid previous, AminoAcid prePrevious) {
        this(input, previous, prePrevious, List.of());
    }

    public DummyAcidsViterbiStep(List<AminoAcid> nextValues) {
        this(AminoAcid.INVALID, AminoAcid.INVALID, AminoAcid.INVALID, nextValues);
    }

    @Override
    public ViterbiStep getPrevious() {
        List<AminoAcid> newNextValues = new ArrayList<>(nextValues);
        newNextValues.add(input);
        return new DummyAcidsViterbiStep(prev, prePrev, AminoAcid.INVALID, newNextValues);
    }

    @Override
    public AminoAcid getInput() {
        return input;
    }

    @Override
    public List<AminoAcid> getNextValues() {
        return nextValues;
    }

    @Override
    public BigDecimal getProbabilityFor(HMMState state) {
        return BigDecimal.valueOf(0.7);
    }
}

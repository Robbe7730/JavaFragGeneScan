package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy ViterbiStep class faking the PathProbabilities
 */
public class DummyPPViterbiStep extends ViterbiStep {
    private final List<HMMState> previousStates;
    private final List<AminoAcid> inputs;

    public DummyPPViterbiStep(List<HMMState> previousStates, List<AminoAcid> inputs) {
        assert previousStates.size() == inputs.size();
        this.previousStates = previousStates;
        this.inputs = inputs;
    }

    @Override
    public HMMState getHighestProbabilityState() {
        return previousStates.get(0);
    }

    @Override
    public PathProbability getPathProbabilityFor(HMMState state) {
        return new PathProbability(previousStates.get(0), 1);
    }

    @Override
    public AminoAcid getInput() {
        return inputs.get(0);
    }

    @Override
    public ViterbiStep getPrevious() {
        List<HMMState> newStates = new ArrayList<>(previousStates);
        List<AminoAcid> newInputs = new ArrayList<>(inputs);

        newStates.remove(0);
        newInputs.remove(0);

        return new DummyPPViterbiStep(newStates, newInputs);
    }
}

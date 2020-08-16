package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import be.robbevanherck.javafraggenescan.transitions.InsertTransition;

public class DummyInsertTransition extends InsertTransition {
    public PathProbability lowPathProbability = new PathProbability(HMMState.INSERT_1, 0.1);
    public PathProbability highPathProbability = new PathProbability(HMMState.INSERT_2, 0.2);

    /**
     * Create a new Dummy InsertTransition (getProbabilityFromInsertion and getProbabilityFromMatch
     * are mocked, calculatePathProbability isn't.
     */
    public DummyInsertTransition() {
        super(HMMState.NO_STATE);
    }

    @Override
    protected PathProbability getProbabilityFromInsertion(HMMParameters parameters, ViterbiStep currentStep) {
        return highPathProbability;
    }

    @Override
    protected PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep currentStep) {
        return lowPathProbability;
    }
}

package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import be.robbevanherck.javafraggenescan.transitions.Transition;

import java.math.BigDecimal;

public class DummyTransition extends Transition {
    public PathProbability pathProbability;

    /**
     * Create a new DummyTransition
     */
    public DummyTransition() {
        super(HMMState.NO_STATE);
        pathProbability = new PathProbability(HMMState.MATCH_1, BigDecimal.valueOf(0.99));
    }

    @Override
    public PathProbability calculatePathProbability(ViterbiStep currentStep) {
        return pathProbability;
    }
}

package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.*;
import be.robbevanherck.javafraggenescan.transitions.MatchTransition;

import java.math.BigDecimal;

public class DummyMatchTransition extends MatchTransition {
    public PathProbability lowerPathProbability = new PathProbability(HMMState.INSERT_1, BigDecimal.valueOf(0.1));
    public PathProbability lowPathProbability = new PathProbability(HMMState.MATCH_1, BigDecimal.valueOf(0.2));
    public PathProbability middlePathProbability = new PathProbability(HMMState.MATCH_2, BigDecimal.valueOf(0.3));
    public PathProbability highPathProbability = new PathProbability(HMMState.START, BigDecimal.valueOf(0.4));

    /**
     * Create a new Dummy MatchTransition (all calculation methods are mocked)
     * are mocked, calculatePathProbability isn't.
     */
    public DummyMatchTransition() {
        super(HMMState.NO_STATE);
    }

    @Override
    protected boolean isCorrectStopCodon(Triple<AminoAcid> tripleToCheck) {
        return false;
    }

    @Override
    protected PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        return lowPathProbability;
    }

    @Override
    protected PathProbability getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        return middlePathProbability;
    }

    @Override
    protected PathProbability getProbabilityFromStart(ViterbiStep currentStep, Triple<AminoAcid> codonEndingAtT) {
        return highPathProbability;
    }

    @Override
    protected PathProbability getProbabilityFromInsertion(HMMParameters parameters, ViterbiStep currentStep) {
        return lowerPathProbability;
    }
}

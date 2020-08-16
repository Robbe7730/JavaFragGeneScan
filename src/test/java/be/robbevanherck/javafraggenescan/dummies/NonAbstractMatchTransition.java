package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.*;
import be.robbevanherck.javafraggenescan.transitions.InsertTransition;
import be.robbevanherck.javafraggenescan.transitions.MatchTransition;

import java.math.BigDecimal;

public class NonAbstractMatchTransition extends MatchTransition {
    /**
     * Create a new Dummy InsertTransition that isn't abstract.
     */
    public NonAbstractMatchTransition() {
        super(HMMState.NO_STATE);
    }

    @Override
    protected boolean isCorrectStopCodon(Triple<AminoAcid> tripleToCheck) {
        return false;
    }

    @Override
    protected PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        return new PathProbability(HMMState.MATCH_1, BigDecimal.valueOf(0.01));
    }

    @Override
    protected PathProbability getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        return new PathProbability(HMMState.MATCH_2, BigDecimal.valueOf(0.02));
    }

    @Override
    protected PathProbability getProbabilityFromStart(ViterbiStep currentStep, Triple<AminoAcid> codonEndingAtT) {
        return new PathProbability(HMMState.START, BigDecimal.valueOf(0.03));
    }
}

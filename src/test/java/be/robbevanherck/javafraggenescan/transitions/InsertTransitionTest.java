package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.dummies.DummyHMMParameters;
import be.robbevanherck.javafraggenescan.dummies.DummyInsertTransition;
import be.robbevanherck.javafraggenescan.dummies.DummyAcidsViterbiStep;
import be.robbevanherck.javafraggenescan.dummies.NonAbstractInsertTransition;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InsertTransitionTest {
    // The InsertForwardTransition class is just an instance of InsertTransition and thus not separately tested

    private final NonAbstractInsertTransition actualInsertTransition = new NonAbstractInsertTransition();
    private final DummyInsertTransition dummyInsertTransition = new DummyInsertTransition();
    private final DummyAcidsViterbiStep dummyAcidsViterbiStep = new DummyAcidsViterbiStep(AminoAcid.A, AminoAcid.T, AminoAcid.G);
    private final DummyHMMParameters dummyParameters = new DummyHMMParameters();

    @Test
    public void calculatePathProbability() {
        // Make sure it returns the highest value PathProbability
        assertEquals(dummyInsertTransition.highPathProbability, dummyInsertTransition.calculatePathProbability(dummyAcidsViterbiStep));
    }

    @Test
    public void getProbabilityFromInsertion() {
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.NO_STATE,BigDecimal.valueOf(0.014)),
                actualInsertTransition.getProbabilityFromInsertion(dummyParameters, dummyAcidsViterbiStep)
        );
    }

    @Test
    public void getProbabilityFromMatch() {
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.NO_STATE, BigDecimal.valueOf(0.021)),
                actualInsertTransition.getProbabilityFromMatch(dummyParameters, dummyAcidsViterbiStep)
        );
    }

    @Test
    public void insertForwardSixthTransition() {
        InsertForwardSixthTransition sixthTransition = new InsertForwardSixthTransition();
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.MATCH_6, BigDecimal.valueOf(0.0084)),
                sixthTransition.getProbabilityFromMatch(dummyParameters, dummyAcidsViterbiStep)
        );
    }

    @Test
    public void insertReverseSixthTransition() {
        InsertReverseSixthTransition sixthTransition = new InsertReverseSixthTransition();
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.MATCH_REVERSE_6, BigDecimal.valueOf(0.0084)),
                sixthTransition.getProbabilityFromMatch(dummyParameters, dummyAcidsViterbiStep)
        );
    }
}
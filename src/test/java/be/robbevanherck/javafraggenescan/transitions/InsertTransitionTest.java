package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.dummies.DummyHMMParameters;
import be.robbevanherck.javafraggenescan.dummies.DummyInsertTransition;
import be.robbevanherck.javafraggenescan.dummies.DummyViterbiStep;
import be.robbevanherck.javafraggenescan.dummies.NonAbstractInsertTransition;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsertTransitionTest {
    // The InsertForwardTransition class is just an instance of InsertTransition and thus not separately tested

    private NonAbstractInsertTransition actualInsertTransition = new NonAbstractInsertTransition();
    private DummyInsertTransition dummyInsertTransition = new DummyInsertTransition();
    private DummyViterbiStep dummyViterbiStep = new DummyViterbiStep(AminoAcid.A, AminoAcid.T, AminoAcid.G);
    private DummyHMMParameters dummyParameters = new DummyHMMParameters();

    @Test
    public void calculatePathProbability() {
        // Make sure it returns the highest value PathProbability
        assertEquals(dummyInsertTransition.highPathProbability, dummyInsertTransition.calculatePathProbability(dummyViterbiStep));
    }

    @Test
    public void getProbabilityFromInsertion() {
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.NO_STATE, 0.7 * 0.1 * 0.2),
                actualInsertTransition.getProbabilityFromInsertion(dummyParameters, dummyViterbiStep)
        );
    }

    @Test
    public void getProbabilityFromMatch() {
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.NO_STATE, 0.7 * 0.1 * 0.3),
                actualInsertTransition.getProbabilityFromMatch(dummyParameters, dummyViterbiStep)
        );
    }

    @Test
    public void insertForwardSixthTransition() {
        InsertForwardSixthTransition sixthTransition = new InsertForwardSixthTransition();
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.MATCH_6, 0.7 * 0.1 * 0.3 * 0.4),
                sixthTransition.getProbabilityFromMatch(dummyParameters, dummyViterbiStep)
        );
    }

    @Test
    public void insertReverseSixthTransition() {
        InsertReverseSixthTransition sixthTransition = new InsertReverseSixthTransition();
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.MATCH_REVERSE_6, 0.7 * 0.1 * 0.3 * 0.4),
                sixthTransition.getProbabilityFromMatch(dummyParameters, dummyViterbiStep)
        );
    }
}
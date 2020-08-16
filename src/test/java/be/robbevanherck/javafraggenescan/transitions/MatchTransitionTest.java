package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.dummies.*;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTransitionTest {
    private NonAbstractMatchTransition actualMatchTransition = new NonAbstractMatchTransition();
    private DummyMatchTransition dummyMatchTransition = new DummyMatchTransition();
    private DummyAcidsViterbiStep dummyAcidsViterbiStep = new DummyAcidsViterbiStep(AminoAcid.A, AminoAcid.T, AminoAcid.G);
    private DummyHMMParameters dummyParameters = new DummyHMMParameters();

    @Test
    public void calculatePathProbability() {
        // Make sure it returns the highest value PathProbability
        assertEquals(dummyMatchTransition.highPathProbability, dummyMatchTransition.calculatePathProbability(dummyAcidsViterbiStep));
    }

    @Test
    public void getProbabilityFromInsertion() {
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.NO_STATE, 0.7 * 0.1 * 0.25),
                actualMatchTransition.getProbabilityFromInsertion(dummyParameters, dummyAcidsViterbiStep)
        );
    }
}
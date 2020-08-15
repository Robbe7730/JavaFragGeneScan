package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.dummies.DummyTransition;
import be.robbevanherck.javafraggenescan.dummies.DummyAcidsViterbiStep;
import be.robbevanherck.javafraggenescan.entities.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static be.robbevanherck.javafraggenescan.TestUtil.aminoAcids;
import static org.junit.Assert.*;

public class TransitionTest {
    private static DummyAcidsViterbiStep curr;
    private static DummyTransition dummyTransition;

    @BeforeClass
    public static void setup() {
        curr = new DummyAcidsViterbiStep(AminoAcid.A, AminoAcid.T, AminoAcid.G);
        dummyTransition = new DummyTransition();
    }

    @Test
    public void calculateStateTransition() {
        // The default calculateStateTransition just sets the value of the ViterbiState in
        // the ViterbiStep to the value of calculatePathProbability (which is subclass-dependent)

        dummyTransition.calculateStateTransition(curr);

        assertEquals(dummyTransition.pathProbability, curr.getPathProbabilityFor(HMMState.NO_STATE));
    }

    @Test
    public void getToState() {
        assertEquals(HMMState.NO_STATE, dummyTransition.getToState());
    }

    @Test
    public void getCodonEndingAtT() {
        for (AminoAcid firstAcid : aminoAcids) {
            for (AminoAcid secondAcid : aminoAcids) {
                for (AminoAcid thirdAcid : aminoAcids) {
                    assertEquals(
                            new Triple<>(thirdAcid, secondAcid, firstAcid),
                            dummyTransition.getCodonEndingAtT(new DummyAcidsViterbiStep(firstAcid, secondAcid, thirdAcid))
                    );
                }
            }
        }
    }

    @Test
    public void getCodonStartingAtT() {
        // getCodonStartingAtX uses this directly, so testing it is not required

        // No values
        DummyAcidsViterbiStep emptyStep = new DummyAcidsViterbiStep(List.of());
        assertNull(dummyTransition.getCodonStartingAtX(emptyStep, 0));
        assertNull(dummyTransition.getCodonStartingAtX(emptyStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(emptyStep, -1));

        // One value
        DummyAcidsViterbiStep singleStep = new DummyAcidsViterbiStep(List.of(AminoAcid.A));
        assertNull(dummyTransition.getCodonStartingAtX(singleStep, 0));
        assertNull(dummyTransition.getCodonStartingAtX(singleStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(singleStep, -1));

        // Two values
        DummyAcidsViterbiStep doubleStep = new DummyAcidsViterbiStep(List.of(AminoAcid.C, AminoAcid.G));
        assertNull(dummyTransition.getCodonStartingAtX(doubleStep, 0));
        assertNull(dummyTransition.getCodonStartingAtX(doubleStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(doubleStep, -1));

        // Three values
        DummyAcidsViterbiStep tripleViterbiStep = new DummyAcidsViterbiStep(List.of(AminoAcid.T, AminoAcid.A, AminoAcid.C));
        assertEquals(new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.C), dummyTransition.getCodonStartingAtX(
                tripleViterbiStep, 0
        ));
        assertNull(dummyTransition.getCodonStartingAtX(tripleViterbiStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(tripleViterbiStep, -1));

        // Four values
        DummyAcidsViterbiStep quadViterbiStep = new DummyAcidsViterbiStep(List.of(AminoAcid.G, AminoAcid.T, AminoAcid.A, AminoAcid.C));
        assertEquals(new Triple<>(AminoAcid.G, AminoAcid.T, AminoAcid.A), dummyTransition.getCodonStartingAtX(
                quadViterbiStep, 0
        ));
        assertEquals(new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.C), dummyTransition.getCodonStartingAtX(
                quadViterbiStep, 1
        ));
        assertNull(dummyTransition.getCodonStartingAtX(quadViterbiStep, 2));
        assertNull(dummyTransition.getCodonStartingAtX(quadViterbiStep, -1));
    }
}
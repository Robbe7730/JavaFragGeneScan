package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.dummies.DummyTransition;
import be.robbevanherck.javafraggenescan.dummies.DummyViterbiStep;
import be.robbevanherck.javafraggenescan.entities.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static be.robbevanherck.javafraggenescan.TestUtil.aminoAcids;
import static org.junit.Assert.*;

public class TransitionTest {
    private static DummyViterbiStep curr;
    private static DummyTransition dummyTransition;

    @BeforeClass
    public static void setup() {
        curr = new DummyViterbiStep(AminoAcid.A, AminoAcid.T, AminoAcid.G);
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
                            dummyTransition.getCodonEndingAtT(new DummyViterbiStep(firstAcid, secondAcid, thirdAcid))
                    );
                }
            }
        }
    }

    @Test
    public void getCodonStartingAtT() {
        // getCodonStartingAtX uses this directly, so testing it is not required

        // No values
        DummyViterbiStep emptyStep = new DummyViterbiStep(List.of());
        assertNull(dummyTransition.getCodonStartingAtX(emptyStep, 0));
        assertNull(dummyTransition.getCodonStartingAtX(emptyStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(emptyStep, -1));

        // One value
        DummyViterbiStep singleStep = new DummyViterbiStep(List.of(AminoAcid.A));
        assertNull(dummyTransition.getCodonStartingAtX(singleStep, 0));
        assertNull(dummyTransition.getCodonStartingAtX(singleStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(singleStep, -1));

        // Two values
        DummyViterbiStep doubleStep = new DummyViterbiStep(List.of(AminoAcid.C, AminoAcid.G));
        assertNull(dummyTransition.getCodonStartingAtX(doubleStep, 0));
        assertNull(dummyTransition.getCodonStartingAtX(doubleStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(doubleStep, -1));

        // Three values
        DummyViterbiStep tripleViterbiStep = new DummyViterbiStep(List.of(AminoAcid.T, AminoAcid.A, AminoAcid.C));
        assertEquals(new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.C), dummyTransition.getCodonStartingAtX(
                tripleViterbiStep, 0
        ));
        assertNull(dummyTransition.getCodonStartingAtX(tripleViterbiStep, 1));
        assertNull(dummyTransition.getCodonStartingAtX(tripleViterbiStep, -1));

        // Four values
        DummyViterbiStep quadViterbiStep = new DummyViterbiStep(List.of(AminoAcid.G, AminoAcid.T, AminoAcid.A, AminoAcid.C));
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
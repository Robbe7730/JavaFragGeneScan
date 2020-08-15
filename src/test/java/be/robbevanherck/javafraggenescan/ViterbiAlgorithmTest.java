package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.dummies.DummyPPViterbiStep;
import be.robbevanherck.javafraggenescan.dummies.DummyPrintStream;
import be.robbevanherck.javafraggenescan.entities.*;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import static be.robbevanherck.javafraggenescan.entities.HMMState.*;
import static be.robbevanherck.javafraggenescan.entities.AminoAcid.*;
import static org.junit.Assert.*;

public class ViterbiAlgorithmTest {

    @Test
    public void testTransitions() {
        // Make sure we have the same amount of transitions as described in the docs (TODO: make transition diagram)
        assertEquals(29, ViterbiAlgorithm.TRANSITIONS.size());
    }

    @Test
    public void run() {
        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));

        // Create a new ViterbiAlgorithm
        ViterbiInput input = new ViterbiInput("TESTING", List.of(AminoAcid.G, AminoAcid.A));
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        // Make sure wholeGenome is passed to the HMMParameters
        assertTrue(algorithm.parameters.wholeGenome());

        // Run the algorithm
        // TODO: make tests with correct forward and reverse strands
        ViterbiStep lastStep = algorithm.run();

        // TODO: backTrack is too complex, narrow it down and test separately
        algorithm.backTrack(lastStep, 0);
    }

    @Test
    public void backTrackForwardClean() {
        // Test a forward chain with no insertions or deletions, clean start and end states

        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));
        // Create a new ViterbiAlgorithm with unused ViterbiInput
        ViterbiInput input = new ViterbiInput("TESTING", List.of(A));
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        Set<ViterbiResult> results = algorithm.backTrack(new DummyPPViterbiStep(
                List.of(
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        END,
                        END,
                        END,
                        MATCH_3,
                        MATCH_2,
                        MATCH_1,
                        MATCH_6,
                        MATCH_5,
                        MATCH_4,
                        MATCH_3,
                        MATCH_2,
                        MATCH_1,
                        START,
                        START,
                        START,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NO_STATE
                ),
                List.of(G,C,C,A,A,T,A,A,A,C,T,C,A,T,G,G,T,A,T,G,C,T,A,INVALID)
        ), 23);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        DummyPrintStream outputPrintStream = new DummyPrintStream();

        firstResult.writeFasta(outputPrintStream);

        assertEquals(">TESTING_8_16_+\nGTACTCAAA\n", outputPrintStream.getResult());
    }

    @Test
    public void backTrackReverseClean() {
        // Test a reverse chain with no insertions or deletions, clean start and end states

        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));
        // Create a new ViterbiAlgorithm with unused ViterbiInput
        ViterbiInput input = new ViterbiInput("TESTING", List.of(A));
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        Set<ViterbiResult> results = algorithm.backTrack(new DummyPPViterbiStep(
                List.of(
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        END_REVERSE,
                        END_REVERSE,
                        END_REVERSE,
                        MATCH_REVERSE_3,
                        MATCH_REVERSE_2,
                        MATCH_REVERSE_1,
                        MATCH_REVERSE_6,
                        MATCH_REVERSE_5,
                        MATCH_REVERSE_4,
                        MATCH_REVERSE_3,
                        MATCH_REVERSE_2,
                        MATCH_REVERSE_1,
                        START_REVERSE,
                        START_REVERSE,
                        START_REVERSE,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NO_STATE
                ),
                List.of(G,C,C,A,A,T,A,A,A,C,T,C,A,T,G,G,T,A,T,G,C,T,A,INVALID)
        ), 23);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        DummyPrintStream outputPrintStream = new DummyPrintStream();

        firstResult.writeFasta(outputPrintStream);

        assertEquals(">TESTING_8_16_-\nTTTGAGTAC\n", outputPrintStream.getResult());
    }

    public void backTrackForwardDirty() {
        // Test a forward chain with insertions or deletions

        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));
        // Create a new ViterbiAlgorithm with unused ViterbiInput
        ViterbiInput input = new ViterbiInput("TESTING", List.of(A));
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        Set<ViterbiResult> results = algorithm.backTrack(new DummyPPViterbiStep(
                List.of(
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        END,
                        END,
                        END,
                        MATCH_3,
                        MATCH_1,
                        MATCH_6,
                        MATCH_5,
                        MATCH_4,
                        MATCH_3,
                        MATCH_2,
                        INSERT_1,
                        MATCH_1,
                        START,
                        START,
                        START,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NON_CODING,
                        NO_STATE
                ),
                List.of(INVALID,G,C,C,A,A,T,A,A,C,T,C,A,T,A,G,G,T,A,T,G,C,T,A)
        ), 23);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        assertEquals("GTACTCAAA", firstResult.getDNA());
    }

}
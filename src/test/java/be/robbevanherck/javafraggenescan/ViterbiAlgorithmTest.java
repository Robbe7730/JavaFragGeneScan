package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.dummies.DummyPPViterbiStep;
import be.robbevanherck.javafraggenescan.dummies.DummyPrintStream;
import be.robbevanherck.javafraggenescan.entities.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static be.robbevanherck.javafraggenescan.entities.AminoAcid.*;
import static be.robbevanherck.javafraggenescan.entities.HMMState.*;
import static org.junit.Assert.*;

public class ViterbiAlgorithmTest {

    @Test
    public void testTransitions() {
        // Make sure we have the same amount of transitions as described in the docs and in the paper
        assertEquals(29, ViterbiAlgorithm.TRANSITIONS.size());
    }

    @Test
    public void run() {
        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));

        // The (unused) inputs, need to be > 2 so the algorithm will run one step
        List<AminoAcid> inputs = new ArrayList<>();

        inputs.add(AminoAcid.G);
        inputs.add(AminoAcid.A);

        // Create a new ViterbiAlgorithm
        ViterbiInput input = new ViterbiInput("TESTING", inputs);
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        // Make sure wholeGenome is passed to the HMMParameters
        assertTrue(algorithm.parameters.wholeGenome());

        // Run the algorithm
        ViterbiStep lastStep = algorithm.run();

        // Test if the input
        assertNull(lastStep);
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

        assertEquals(">TESTING_9_17_+\nGTACTCAAA\n", outputPrintStream.getResult());
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

        assertEquals(">TESTING_9_17_-\nTTTGAGTAC\n", outputPrintStream.getResult());
    }

    @Test
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
                List.of(G, C, C, A, A, T, A, A, C, T, C, A, T, A, G, G, T, A, T, G, C, T, A, INVALID)
        ), 23);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        DummyPrintStream outputPrintStream = new DummyPrintStream();

        firstResult.writeFasta(outputPrintStream);

        assertEquals(">TESTING_9_17_+\nGTACTCANA\n", outputPrintStream.getResult());
    }

    @Test
    public void backTrackReverseDirty() {
        // Test a reverse chain with insertions and deletions

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
                        MATCH_REVERSE_1,
                        MATCH_REVERSE_6,
                        MATCH_REVERSE_5,
                        MATCH_REVERSE_4,
                        MATCH_REVERSE_3,
                        MATCH_REVERSE_2,
                        INSERT_REVERSE_1,
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
                List.of(G, C, C, A, A, T, A, A, C, T, C, A, T, A, G, G, T, A, T, G, C, T, A, INVALID)
        ), 23);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        DummyPrintStream outputPrintStream = new DummyPrintStream();

        firstResult.writeFasta(outputPrintStream);

        assertEquals(">TESTING_9_17_-\nTNTGAGTAC\n", outputPrintStream.getResult());
    }


    @Test
    public void backtrackForwardPruning() {
        // Test a forward chain with left-over match states

        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));
        // Create a new ViterbiAlgorithm with unused ViterbiInput
        ViterbiInput input = new ViterbiInput("TESTING", List.of(A));
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        Set<ViterbiResult> results = algorithm.backTrack(new DummyPPViterbiStep(
                List.of(
                        MATCH_2,
                        MATCH_1,
                        MATCH_6,
                        MATCH_5,
                        MATCH_4,
                        MATCH_3,
                        MATCH_2,
                        MATCH_1,
                        NO_STATE
                ),
                List.of(A, A, C, T, C, A, T, G, INVALID)
        ), 8);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        DummyPrintStream outputPrintStream = new DummyPrintStream();

        firstResult.writeFasta(outputPrintStream);

        assertEquals(">TESTING_1_6_+\nGTACTC\n", outputPrintStream.getResult());
    }

    @Test
    public void backtrackReversePruning() {
        // Test a reverse chain with left-over match states

        // Set up the HMMParameters with an unused input file
        HMMParameters.setup(new File("train/454_10"));
        // Create a new ViterbiAlgorithm with unused ViterbiInput
        ViterbiInput input = new ViterbiInput("TESTING", List.of(A));
        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, true);

        Set<ViterbiResult> results = algorithm.backTrack(new DummyPPViterbiStep(
                List.of(
                        MATCH_REVERSE_2,
                        MATCH_REVERSE_1,
                        MATCH_REVERSE_6,
                        MATCH_REVERSE_5,
                        MATCH_REVERSE_4,
                        MATCH_REVERSE_3,
                        MATCH_REVERSE_2,
                        MATCH_REVERSE_1,
                        NO_STATE
                ),
                List.of(A, A, C, T, C, A, T, G, INVALID)
        ), 8);

        assertEquals(1, results.size());

        ViterbiResult firstResult = results.iterator().next();

        DummyPrintStream outputPrintStream = new DummyPrintStream();

        firstResult.writeFasta(outputPrintStream);

        assertEquals(">TESTING_1_6_-\nGAGTAC\n", outputPrintStream.getResult());
    }
}
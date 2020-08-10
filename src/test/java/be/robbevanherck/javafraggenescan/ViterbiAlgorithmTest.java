package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ViterbiAlgorithmTest {

    @Test
    public void testTransitions() {
        // Make sure we have the same amount of transitions as described in the docs (TODO: make transition diagram)
        assertEquals(29, ViterbiAlgorithm.TRANSITIONS.size());
    }

    @Test
    public void run() throws IOException {
        // Set up the HMMParameters with an empty input file
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
}
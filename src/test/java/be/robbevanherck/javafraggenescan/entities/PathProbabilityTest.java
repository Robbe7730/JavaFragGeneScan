package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathProbabilityTest {

    @Test
    public void max() {
        PathProbability firstPathProbability = new PathProbability(HMMState.START, 0.1);
        PathProbability secondPathProbability = new PathProbability(HMMState.NON_CODING, 0.5);
        PathProbability thirdPathProbability = new PathProbability(HMMState.MATCH_6, 0.9);

        assertEquals(secondPathProbability, PathProbability.max(firstPathProbability, secondPathProbability));

        assertEquals(thirdPathProbability, PathProbability.max(firstPathProbability, secondPathProbability, thirdPathProbability));
    }

    @Test
    public void getSetValues() {
        PathProbability testPP = new PathProbability(HMMState.INSERT_REVERSE_1, 0.8);
        assertEquals(0.8, testPP.getProbability(), 0);
        assertEquals(HMMState.INSERT_REVERSE_1, testPP.getPreviousState());

        testPP.setProbability(0.2);
        assertEquals(0.2, testPP.getProbability(), 0);
        assertEquals(HMMState.INSERT_REVERSE_1, testPP.getPreviousState());
    }
}
package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PathProbabilityTest {

    @Test
    public void max() {
        PathProbability firstPathProbability = new PathProbability(HMMState.START, BigDecimal.valueOf(0.1));
        PathProbability secondPathProbability = new PathProbability(HMMState.NON_CODING, BigDecimal.valueOf(0.5));
        PathProbability thirdPathProbability = new PathProbability(HMMState.MATCH_6, BigDecimal.valueOf(0.9));

        assertEquals(secondPathProbability, PathProbability.max(firstPathProbability, secondPathProbability));

        assertEquals(thirdPathProbability, PathProbability.max(firstPathProbability, secondPathProbability, thirdPathProbability));
    }

    @Test
    public void getSetValues() {
        PathProbability testPP = new PathProbability(HMMState.INSERT_REVERSE_1, BigDecimal.valueOf(0.8));
        assertEquals(BigDecimal.valueOf(0.8), testPP.getProbability());
        assertEquals(HMMState.INSERT_REVERSE_1, testPP.getPreviousState());

        testPP.setProbability(BigDecimal.valueOf(0.2));
        assertEquals(BigDecimal.valueOf(0.2), testPP.getProbability());
        assertEquals(HMMState.INSERT_REVERSE_1, testPP.getPreviousState());
    }
}
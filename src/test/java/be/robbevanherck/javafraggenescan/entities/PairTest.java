package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void testPair() {
        Pair<Integer> testPair = new Pair<>(1, 2);

        assertEquals(1, (long) testPair.getFirstValue());
        assertEquals(2, (long) testPair.getSecondValue());

        Triple<Integer> testTriple = testPair.append(3);
        assertEquals(new Triple<>(1, 2, 3), testTriple);
    }
}
package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class TripleTest {

    @Test
    public void getValue() {
        Triple<Integer> testTriple = new Triple<>(1, 2, 3);

        assertEquals(1, (long) testTriple.getFirstValue());
        assertEquals(2, (long) testTriple.getSecondValue());
        assertEquals(3, (long) testTriple.getThirdValue());
    }
}
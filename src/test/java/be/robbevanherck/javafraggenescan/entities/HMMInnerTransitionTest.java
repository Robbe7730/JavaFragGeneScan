package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class HMMInnerTransitionTest {

    @Test
    public void fromString() {
        assertEquals(HMMInnerTransition.MATCH_MATCH, HMMInnerTransition.fromString("MM"));
        assertEquals(HMMInnerTransition.MATCH_INSERT, HMMInnerTransition.fromString("MI"));
        assertEquals(HMMInnerTransition.MATCH_DELETE, HMMInnerTransition.fromString("MD"));
        assertEquals(HMMInnerTransition.INSERT_INSERT, HMMInnerTransition.fromString("II"));
        assertEquals(HMMInnerTransition.INSERT_MATCH, HMMInnerTransition.fromString("IM"));
        assertEquals(HMMInnerTransition.DELETE_DELETE, HMMInnerTransition.fromString("DD"));
        assertEquals(HMMInnerTransition.DELETE_MATCH, HMMInnerTransition.fromString("DM"));

        assertEquals(HMMInnerTransition.INVALID_TRANSITION, HMMInnerTransition.fromString("XX"));
        assertEquals(HMMInnerTransition.INVALID_TRANSITION, HMMInnerTransition.fromString(""));
        assertEquals(HMMInnerTransition.INVALID_TRANSITION, HMMInnerTransition.fromString("MMM"));
        assertEquals(HMMInnerTransition.INVALID_TRANSITION, HMMInnerTransition.fromString("ID"));
    }
}
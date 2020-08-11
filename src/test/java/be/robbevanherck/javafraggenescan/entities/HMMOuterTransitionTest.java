package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class HMMOuterTransitionTest {

    @Test
    public void fromString() {
        assertEquals(HMMOuterTransition.GENE_GENE, HMMOuterTransition.fromString("GG"));
        assertEquals(HMMOuterTransition.GENE_END, HMMOuterTransition.fromString("GE"));
        assertEquals(HMMOuterTransition.END_NONCODING, HMMOuterTransition.fromString("ER"));
        assertEquals(HMMOuterTransition.END_START_SAME, HMMOuterTransition.fromString("ES"));
        assertEquals(HMMOuterTransition.END_START_REVERSE, HMMOuterTransition.fromString("ES1"));
        assertEquals(HMMOuterTransition.NONCODING_START, HMMOuterTransition.fromString("RS"));
        assertEquals(HMMOuterTransition.NONCODING_NONCODING, HMMOuterTransition.fromString("RR"));

        assertEquals(HMMOuterTransition.INVALID_TRANSITION, HMMOuterTransition.fromString("EG"));
        assertEquals(HMMOuterTransition.INVALID_TRANSITION, HMMOuterTransition.fromString("XX"));
        assertEquals(HMMOuterTransition.INVALID_TRANSITION, HMMOuterTransition.fromString(""));
        assertEquals(HMMOuterTransition.INVALID_TRANSITION, HMMOuterTransition.fromString("GGG"));
    }
}
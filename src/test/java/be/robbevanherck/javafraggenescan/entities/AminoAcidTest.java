package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class AminoAcidTest {

    @Test
    public void fromInt() {
        assertEquals(AminoAcid.A, AminoAcid.fromInt(0));
        assertEquals(AminoAcid.C, AminoAcid.fromInt(1));
        assertEquals(AminoAcid.G, AminoAcid.fromInt(2));
        assertEquals(AminoAcid.T, AminoAcid.fromInt(3));
        assertEquals(AminoAcid.INVALID, AminoAcid.fromInt(4));
        assertEquals(AminoAcid.INVALID, AminoAcid.fromInt(-1));
    }

    @Test
    public void toInt() {
        assertEquals(0, AminoAcid.toInt(AminoAcid.A));
        assertEquals(1, AminoAcid.toInt(AminoAcid.C));
        assertEquals(2, AminoAcid.toInt(AminoAcid.G));
        assertEquals(3, AminoAcid.toInt(AminoAcid.T));
        assertEquals(-1, AminoAcid.toInt(AminoAcid.INVALID));
    }

    @Test
    public void fromString() {
        assertEquals(AminoAcid.A, AminoAcid.fromString("A"));
        assertEquals(AminoAcid.C, AminoAcid.fromString("C"));
        assertEquals(AminoAcid.G, AminoAcid.fromString("G"));
        assertEquals(AminoAcid.T, AminoAcid.fromString("T"));

        assertEquals(AminoAcid.INVALID, AminoAcid.fromString("N"));
        assertEquals(AminoAcid.INVALID, AminoAcid.fromString(""));
        assertEquals(AminoAcid.INVALID, AminoAcid.fromString("AA"));
    }

    @Test
    public void testToString() {
        assertEquals("A", AminoAcid.A.toString());
        assertEquals("C", AminoAcid.C.toString());
        assertEquals("G", AminoAcid.G.toString());
        assertEquals("T", AminoAcid.T.toString());
        assertEquals("N", AminoAcid.INVALID.toString());
    }
}
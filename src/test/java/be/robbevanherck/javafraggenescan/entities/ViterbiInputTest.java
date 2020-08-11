package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViterbiInputTest {

    @Test
    public void getValues() {
        ViterbiInput testInput = new ViterbiInput("TEST", List.of(AminoAcid.A, AminoAcid.T));

        assertEquals("TEST", testInput.getName());
        assertEquals(List.of(AminoAcid.A, AminoAcid.T), testInput.getInputAcids());
    }
}
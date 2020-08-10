package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Triple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StartStopUtilTest {

    @FunctionalInterface
    interface StartStopCodonTester {
        boolean testCodon(Triple<AminoAcid> codon);
    }

    private void testAllOptions(List<Triple<AminoAcid>> correctCodons, StartStopCodonTester tester) {
        // Generate all triples (including INVALID)
        List<Triple<AminoAcid>> codons = new ArrayList<>();

        for (AminoAcid firstAcid : AminoAcid.values()) {
            for (AminoAcid secondAcid : AminoAcid.values()) {
                for (AminoAcid thirdAcid : AminoAcid.values()) {
                    codons.add(new Triple<>(firstAcid, secondAcid, thirdAcid));
                }
            }
        }

        // Test them all
        for (Triple<AminoAcid> codon : codons) {
            if (correctCodons.contains(codon)) {
                assertTrue(codon.toString() + " was not recognized as a correct codon", tester.testCodon(codon));
            } else {
                assertFalse(codon.toString() + " was incorrectly recognized as a correct codon", tester.testCodon(codon));
            }
        }

        // Test null
        assertFalse("null was incorrectly recognized as a correct codon", tester.testCodon(null));
    }

    @Test
    public void isReverseStopCodon() {
        testAllOptions(List.of(
                new Triple<>(AminoAcid.C, AminoAcid.A, AminoAcid.T),
                new Triple<>(AminoAcid.C, AminoAcid.A, AminoAcid.C),
                new Triple<>(AminoAcid.C, AminoAcid.A, AminoAcid.A)
        ), StartStopUtil::isReverseStopCodon);
    }

    @Test
    public void isForwardStopCodon() {
        testAllOptions(List.of(
                new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.A),
                new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.G),
                new Triple<>(AminoAcid.T, AminoAcid.G, AminoAcid.A)
        ), StartStopUtil::isForwardStopCodon);
    }

    @Test
    public void isReverseStartCodon() {
        testAllOptions(List.of(
                new Triple<>(AminoAcid.T, AminoAcid.T, AminoAcid.A),
                new Triple<>(AminoAcid.T, AminoAcid.C, AminoAcid.A),
                new Triple<>(AminoAcid.C, AminoAcid.T, AminoAcid.A)
        ), StartStopUtil::isReverseStartCodon);
    }

    @Test
    public void isForwardStartCodon() {
        testAllOptions(List.of(
                new Triple<>(AminoAcid.A, AminoAcid.T, AminoAcid.G),
                new Triple<>(AminoAcid.G, AminoAcid.T, AminoAcid.G),
                new Triple<>(AminoAcid.T, AminoAcid.T, AminoAcid.G)
        ), StartStopUtil::isForwardStartCodon);
    }
}
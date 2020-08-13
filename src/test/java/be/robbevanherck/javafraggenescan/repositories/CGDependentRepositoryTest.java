package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Pair;
import be.robbevanherck.javafraggenescan.entities.Triple;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class CGDependentRepositoryTest {

    private final List<AminoAcid> validAcids = List.of(
            AminoAcid.A,
            AminoAcid.C,
            AminoAcid.G,
            AminoAcid.T
    );

    private final CGDependentRepository<Integer> dummyCGDependentRepository = new CGDependentRepository<>("train/dummy_training") {
        @Override
        protected Integer readOneBlock(Scanner s) {
            return s.nextInt();
        }

        @Override
        protected int readIndex(Scanner s) {
            String prefixedIndex = s.next(Pattern.compile(">[0-9]*"));

            String indexString = String.copyValueOf(prefixedIndex.toCharArray(), 1, prefixedIndex.length() - 1);

            return Integer.parseInt(indexString);
        }
    };

    @Test
    public void getValues() {
        // Test that the values are actually returned as they are read
        for (int i = 26; i <= 70; i++) {
            assertEquals(100 + i, (long) dummyCGDependentRepository.getValues(i));
        }
    }

    @Test
    public void createDinucleotide() {
        int i = 0;
        for (AminoAcid firstAcid : validAcids) {
            for (AminoAcid secondAcid : validAcids) {
                assertEquals(new Pair<>(firstAcid, secondAcid), dummyCGDependentRepository.createDinucleotide(i));
                i++;
            }
        }
    }

    @Test
    public void createTrinucleotide() {        int i = 0;
        for (AminoAcid firstAcid : validAcids) {
            for (AminoAcid secondAcid : validAcids) {
                for (AminoAcid thirdAcid : validAcids) {
                    assertEquals(new Triple<>(firstAcid, secondAcid, thirdAcid), dummyCGDependentRepository.createTrinucleotide(i));
                    i++;
                }
            }
        }
    }
}
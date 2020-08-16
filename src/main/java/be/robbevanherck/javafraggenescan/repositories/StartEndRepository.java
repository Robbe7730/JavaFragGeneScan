package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Superclass for Repositories for start, start1, stop and stop1
 */
public abstract class StartEndRepository extends CGDependentRepository<Map<Integer, Map<Triple<AminoAcid>, Double>>> {
    /**
     * Create a new StartEndRepository
     *
     * @param filename The file to read
     */
    public StartEndRepository(String filename) {
        super(filename);
    }

    @Override
    protected Map<Integer, Map<Triple<AminoAcid>, Double>> readOneBlock(Scanner s) {
        Map<Integer, Map<Triple<AminoAcid>, Double>> ret = new HashMap<>();
        for (int position = 0; position < 61; position++) {
            Map<Triple<AminoAcid>, Double> newRow = new HashMap<>();
            for (int trinucleotideInteger = 0; trinucleotideInteger < 64; trinucleotideInteger++) {
                double probability = s.nextDouble();
                Triple<AminoAcid> codon = createTrinucleotide(trinucleotideInteger);

                newRow.put(codon, probability);
            }
            ret.put(position, newRow);
        }
        return ret;
    }

    @Override
    protected int readIndex(Scanner s) {
        String prefixedIndex = s.next(Pattern.compile(">[0-9]*"));

        String indexString = String.copyValueOf(prefixedIndex.toCharArray(), 1, prefixedIndex.length() - 1);

        return Integer.parseInt(indexString);
    }
}
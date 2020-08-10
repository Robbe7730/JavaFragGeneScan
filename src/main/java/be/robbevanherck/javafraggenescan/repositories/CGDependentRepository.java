package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.exceptions.InvalidTrainingFileException;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Pair;
import be.robbevanherck.javafraggenescan.entities.Triple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A superclass for all Repositories that give values depending on the percentage of C/G in the input
 * @param <T> The type of the data under the headers
 */
public abstract class CGDependentRepository<T> {

    /**
     * Create a new CGDependentRepository
     * @param filename The file to read
     */
    public CGDependentRepository(String filename) {
        File file = new File(filename);
        try(Scanner s = new Scanner(file)) {
            while(s.hasNext()) {
                // Read the header of this set of values
                int index = readIndex(s);

                matchEmissions.put(index, readOneBlock(s));
            }
        } catch (FileNotFoundException fnef) {
            throw new InvalidTrainingFileException("No such file: " + filename, fnef);
        }
    }

    protected Map<Integer, T> matchEmissions = new HashMap<>();

    /**
     * Get the matchEmissions for a certain amount of G/C amino-acids
     * @param percentageGC The percentage of G/C amino-acids in the input compared to the length of the input
     * @return The mapping or Match-state emissions
     */
    public T getValues(int percentageGC) {
        int countGC = Math.min(Math.max(percentageGC, 26), 70);
        return matchEmissions.get(countGC);
    }

    /**
     * Read one block of data under the header
     * @param s The Scanner to use
     * @return The data
     */
    protected abstract T readOneBlock(Scanner s);

    /**
     * Read the index (some have > in front of it)
     * @param s The Scanner to use
     * @return The index
     */
    protected abstract int readIndex(Scanner s);

    /**
     * Create a Pair of the combination of 2 amino-acid indices
     * @param dinucleotideIndex 2 amino-acid indices (0-16)
     * @return The pair
     */
    protected Pair<AminoAcid> createDinucleotide(int dinucleotideIndex) {
        return new Pair<>(
                AminoAcid.fromInt((dinucleotideIndex / 4) % 4),
                AminoAcid.fromInt(dinucleotideIndex % 4)
        );
    }
    /**
     * Create a Triple of the combination of 3 amino-acid indices
     * @param trinucleotideIndex The combination of 3 amino-acid indices (0-64)
     * @return The triple
     */
    protected Triple<AminoAcid> createTrinucleotide(int trinucleotideIndex) {
        return new Triple<>(
                AminoAcid.fromInt((trinucleotideIndex / 16) % 4),
                AminoAcid.fromInt((trinucleotideIndex / 4) % 4),
                AminoAcid.fromInt(trinucleotideIndex % 4)
        );
    }
}

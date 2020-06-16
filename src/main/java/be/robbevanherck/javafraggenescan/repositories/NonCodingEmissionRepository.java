package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Repository for the noncoding file
 */
public class NonCodingEmissionRepository extends CGDependentRepository<Map<Pair<AminoAcid>, Double>> {
    /**
     * Create a new NonCodingEmissionRepository
     */
    public NonCodingEmissionRepository() {
        super("train/noncoding");
    }

    private static NonCodingEmissionRepository instance;

    @Override
    protected Map<Pair<AminoAcid>, Double> readOneBlock(Scanner s) {
        Map<Pair<AminoAcid>, Double> ret = new HashMap<>();
        for (int aminoAcidInteger = 0; aminoAcidInteger < 16; aminoAcidInteger++) {
                ret.put(createDinucleotide(aminoAcidInteger), s.nextDouble());
        }
        return ret;
    }

    @Override
    protected int readIndex(Scanner s) {
        return s.nextInt();
    }

    /**
     * Create an instance of this Repository
     */
    public static void createInstance() {
        instance = new NonCodingEmissionRepository();
    }

    public static NonCodingEmissionRepository getInstance() {
        return instance;
    }
}

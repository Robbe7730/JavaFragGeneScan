package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.InvalidTrainingFileException;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Repository for the data in the files related to M and M' states (gene and rgene)
 */
public abstract class MatchEmissionRepository {

    protected Map<Integer, Map<HMMState, Map<Triple<AminoAcid>, Double>>> matchEmissions = new HashMap<>();

    protected MatchEmissionRepository(String filename) {
        File file = new File(filename);
        try(Scanner s = new Scanner(file)) {
            while(s.hasNext()) {
                // Read the header of this set of values
                int index = s.nextInt();
                Map<HMMState, Map<Triple<AminoAcid>, Double>> newMapping = new EnumMap<>(HMMState.class);

                // For every M state
                for (int mStateIndex = 0; mStateIndex < 6; mStateIndex++) {
                    HMMState mState = matchStateFromInt(mStateIndex+1);
                    Map<Triple<AminoAcid>, Double> probabilities = new HashMap<>();

                    // For each tri-nucleotide
                    for (int trinucleotideIndex = 0; trinucleotideIndex < 4*4*4; trinucleotideIndex++) {
                        Triple<AminoAcid> trinucleotide = createTrinucleotide(trinucleotideIndex);
                        probabilities.put(trinucleotide, s.nextDouble());
                    }
                    newMapping.put(mState, probabilities);
                }

                matchEmissions.put(index, newMapping);
            }
        } catch (FileNotFoundException fnef) {
            throw new InvalidTrainingFileException("No such file: " + filename, fnef);
        }
    }

    /**
     * Create a Triple of the combination of 3 aminoacid indices
     * @param trinucleotideIndex The combination of 3 aminoacid indices
     * @return The triple
     */
    protected Triple<AminoAcid> createTrinucleotide(int trinucleotideIndex) {
        return new Triple<>(
                AminoAcid.fromInt((trinucleotideIndex / 16) % 4),
                AminoAcid.fromInt((trinucleotideIndex / 4) % 4),
                AminoAcid.fromInt(trinucleotideIndex % 4)
        );
    }
    /**
     * Get the matchEmissions for a certain amount of G/C amino-acids
     * @param percentageGC The percentage of G/C amino-acids in the input compared to the length of the input
     * @return The mapping or Match-state emissions
     */
    public Map<HMMState, Map<Triple<AminoAcid>, Double>> getEmissions(int percentageGC) {
        int countGC = Math.min(Math.max(percentageGC, 26), 70);
        return matchEmissions.get(countGC);
    }

    /**
     *
     * Get the MATCH_x (Mx) or MATCH_REVERSE_x (Mx') state
     * @param i The number of the match state (0-6)
     * @return The MATCH_x enum value or NO_STATE if an invalid number is given
     */
    protected abstract HMMState matchStateFromInt(int i);
}

package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Repository for the data in the files related to M and M' states (gene and rgene)
 */
public abstract class MatchEmissionRepository extends CGDependentRepository<Map<HMMState, Map<Triple<AminoAcid>, BigDecimal>>> {

    protected MatchEmissionRepository(String filename) {
        super(filename);
    }

    @Override
    protected int readIndex(Scanner s) {
        // This file just has numeric headers
        return s.nextInt();
    }

    @Override
    protected Map<HMMState, Map<Triple<AminoAcid>, BigDecimal>> readOneBlock(Scanner s) {
        Map<HMMState, Map<Triple<AminoAcid>, BigDecimal>> newMapping = new EnumMap<>(HMMState.class);

        // For every M state
        for (int mStateIndex = 0; mStateIndex < 6; mStateIndex++) {
            HMMState mState = matchStateFromInt(mStateIndex+1);
            Map<Triple<AminoAcid>, BigDecimal> probabilities = new HashMap<>();

            // For each tri-nucleotide
            for (int trinucleotideIndex = 0; trinucleotideIndex < 4*4*4; trinucleotideIndex++) {
                Triple<AminoAcid> trinucleotide = createTrinucleotide(trinucleotideIndex);
                probabilities.put(trinucleotide, BigDecimal.valueOf(s.nextDouble()));
            }
            newMapping.put(mState, probabilities);
        }

        return newMapping;
    }

    /**
     *
     * Get the MATCH_x (Mx) or MATCH_REVERSE_x (Mx') state
     * @param i The number of the match state (0-6)
     * @return The MATCH_x enum value or NO_STATE if an invalid number is given
     */
    protected abstract HMMState matchStateFromInt(int i);
}

package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;

import java.util.Map;

/**
 * Repository for the data in the 'rgene' file
 */
public class ReverseMatchEmissionRepository extends MatchEmissionRepository {
    private ReverseMatchEmissionRepository() {
        super();
    }

    /**
     * Setup the repository by reading the file
     */
    public static void setup() {
        setup("train/rgene");
    }


    /**
     * Get the reverse match emission probabilities for a certain amount of G/C amino-acids
     * @param percentageGC The percentage of G/C amino-acids in the input compared to the length of the input
     * @return The mapping for Match-state emissions
     */
    public static Map<HMMState, Map<Triple<AminoAcid>, Double>> getReverseMatchEmissionProbabilities(int percentageGC) {
        return getEmissions(percentageGC);
    }
}

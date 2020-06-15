package be.robbevanherck.javafraggenescan.entities;

/**
 * Represent the transitions on the highest level of the HMM
 */
public enum HMMOuterTransition {
    GENE_END,                   // G -> E
    GENE_GENE,                  // G -> B
    END_NONCODING,              // E -> R
    NONCODING_START,            // R -> S
    NONCODING_NONCODING,        // R -> R
    END_START_SAME,             // E -> S or E' -> S'
    END_START_REVERSE,          // E -> S' or E' -> S

    INVALID_TRANSITION;

    /**
     * Create a transition from a String (as they are in the input file)
     * @param transitionString The string
     * @return The HMMOuterTransition corresponding to that string, or INVALID_TRANSITION if it's invalid
     */
    public static HMMOuterTransition fromString(String transitionString) {
        switch (transitionString) {
            case "GG":
                return GENE_GENE;
            case "GE":
                return GENE_END;
            case "ER":
                return END_NONCODING;
            case "ES":
                return END_START_SAME;
            case "ES1":
                return END_START_REVERSE;
            case "RS":
                return NONCODING_START;
            case "RR":
                return NONCODING_NONCODING;
            default:
                return INVALID_TRANSITION;
        }
    }
}

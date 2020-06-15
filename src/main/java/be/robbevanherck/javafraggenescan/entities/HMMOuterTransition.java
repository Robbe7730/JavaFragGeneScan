package be.robbevanherck.javafraggenescan.entities;

/**
 * Represent the transitions on the highest level of the HMM
 */
public enum HMMOuterTransition {
    GENE_END,
    GENE_GENE,
    END_NONCODING,
    NONCODING_START,
    NONCODING_NONCODING,
    END_START_SAME,
    END_START_REVERSE
}

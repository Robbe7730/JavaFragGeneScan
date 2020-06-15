package be.robbevanherck.javafraggenescan.entities;

/**
 * Represent the transitions on the lower level of the HMM (inside Gene state)
 */
public enum HMMInnerTransition {
    MATCH_MATCH,
    MATCH_INSERT,
    MATCH_DELETE,
    INSERT_INSERT,
    INSERT_MATCH,
    DELETE_DELETE,
    DELETE_MATCH
}

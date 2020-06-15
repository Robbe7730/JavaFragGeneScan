package be.robbevanherck.javafraggenescan.entities;

/**
 * All the possible states the HMM can be in
 */
public enum HMMState {
    MATCH_1,
    MATCH_2,
    MATCH_3,
    MATCH_4,
    MATCH_5,
    MATCH_6,
    INSERT_1,
    INSERT_2,
    INSERT_3,
    INSERT_4,
    INSERT_5,
    INSERT_6,
    MATCH_REVERSE_1,
    MATCH_REVERSE_2,
    MATCH_REVERSE_3,
    MATCH_REVERSE_4,
    MATCH_REVERSE_5,
    MATCH_REVERSE_6,
    INSERT_REVERSE_1,
    INSERT_REVERSE_2,
    INSERT_REVERSE_3,
    INSERT_REVERSE_4,
    INSERT_REVERSE_5,
    INSERT_REVERSE_6,
    START,
    START_REVERSE,
    END,
    END_REVERSE,
    NON_MATCHING,
}

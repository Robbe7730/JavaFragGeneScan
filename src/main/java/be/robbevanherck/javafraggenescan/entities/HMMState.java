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

    NO_STATE;

    /**
     * Get the previous state from a state
     * @param state The current state
     * @return The previous state
     */
    public static HMMState previousState(HMMState state) {
        switch (state) {
            case MATCH_1:
                return HMMState.MATCH_2;
            case MATCH_2:
                return HMMState.MATCH_3;
            case MATCH_3:
                return HMMState.MATCH_4;
            case MATCH_4:
                return HMMState.MATCH_5;
            case MATCH_5:
                return HMMState.MATCH_6;
            case MATCH_6:
                return HMMState.MATCH_1;
            default:
                return NO_STATE;
        }
    }

    /**
     * Get the corresponding insert state from a match state
     * @param state The current state
     * @return The previous state
     */
    public static HMMState insertStateFor(HMMState state) {
        switch (state) {
            case MATCH_1:
                return HMMState.INSERT_6;
            case MATCH_2:
                return HMMState.INSERT_1;
            case MATCH_3:
                return HMMState.INSERT_2;
            case MATCH_4:
                return HMMState.INSERT_3;
            case MATCH_5:
                return HMMState.INSERT_4;
            case MATCH_6:
                return HMMState.INSERT_5;
            default:
                return NO_STATE;
        }
    }
}

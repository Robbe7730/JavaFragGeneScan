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
    NON_CODING,

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
            case MATCH_REVERSE_1:
                return HMMState.MATCH_REVERSE_2;
            case MATCH_REVERSE_2:
                return HMMState.MATCH_REVERSE_3;
            case MATCH_REVERSE_3:
                return HMMState.MATCH_REVERSE_4;
            case MATCH_REVERSE_4:
                return HMMState.MATCH_REVERSE_5;
            case MATCH_REVERSE_5:
                return HMMState.MATCH_REVERSE_6;
            case MATCH_REVERSE_6:
                return HMMState.MATCH_REVERSE_1;
            default:
                return NO_STATE;
        }
    }

    /**
     * Get the corresponding insert state from a match state
     * Mx -> I(x-1)
     * @param state The current matching state
     * @return The insert state or NO_STATE if the input is invalid
     */
    public static HMMState insertStateForMatching(HMMState state) {
        // Would this make more sense as Mx -> Ix and use previousState?
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
            case MATCH_REVERSE_1:
                return HMMState.INSERT_REVERSE_6;
            case MATCH_REVERSE_2:
                return HMMState.INSERT_REVERSE_1;
            case MATCH_REVERSE_3:
                return HMMState.INSERT_REVERSE_2;
            case MATCH_REVERSE_4:
                return HMMState.INSERT_REVERSE_3;
            case MATCH_REVERSE_5:
                return HMMState.INSERT_REVERSE_4;
            case MATCH_REVERSE_6:
                return HMMState.INSERT_REVERSE_5;
            default:
                return NO_STATE;
        }
    }

    /**
     * Get the corresponding matching state from a insert state
     * Ix -> Mx
     * @param state The current insert state
     * @return The matching state or NO_STATE if the argument is invalid
     */
    public static HMMState matchingStateForInsert(HMMState state) {
        switch (state) {
            case INSERT_1:
                return HMMState.MATCH_1;
            case INSERT_2:
                return HMMState.MATCH_2;
            case INSERT_3:
                return HMMState.MATCH_3;
            case INSERT_4:
                return HMMState.MATCH_4;
            case INSERT_5:
                return HMMState.MATCH_5;
            case INSERT_6:
                return HMMState.MATCH_6;
            default:
                return NO_STATE;
        }
    }

    /**
     * Create a HMMState from a string (like they are in the input files)
     * @param stateString The string to convert
     * @return The HMMState or NO_STATE if the string is invalid
     */
    public static HMMState fromString(String stateString) {
        switch (stateString) {
            case "S":
                return START;
            case "E":
                return END;
            case "R":
                return NON_CODING;
            case "S_1":
                return START_REVERSE;
            case "E_1":
                return END_REVERSE;
            case "M1":
                return MATCH_1;
            case "M2":
                return MATCH_2;
            case "M3":
                return MATCH_3;
            case "M4":
                return MATCH_4;
            case "M5":
                return MATCH_5;
            case "M6":
                return MATCH_6;
            case "M1_1":
                return MATCH_REVERSE_1;
            case "M2_1":
                return MATCH_REVERSE_2;
            case "M3_1":
                return MATCH_REVERSE_3;
            case "M4_1":
                return MATCH_REVERSE_4;
            case "M5_1":
                return MATCH_REVERSE_5;
            case "M6_1":
                return MATCH_REVERSE_6;
            case "I1":
                return INSERT_1;
            case "I2":
                return INSERT_2;
            case "I3":
                return INSERT_3;
            case "I4":
                return INSERT_4;
            case "I5":
                return INSERT_5;
            case "I6":
                return INSERT_6;
            case "I1_1":
                return INSERT_REVERSE_1;
            case "I2_1":
                return INSERT_REVERSE_2;
            case "I3_1":
                return INSERT_REVERSE_3;
            case "I4_1":
                return INSERT_REVERSE_4;
            case "I5_1":
                return INSERT_REVERSE_5;
            case "I6_1":
                return INSERT_REVERSE_6;
            default:
                return NO_STATE;
        }
    }
}

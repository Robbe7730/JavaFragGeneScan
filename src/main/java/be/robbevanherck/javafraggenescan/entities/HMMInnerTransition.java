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
    DELETE_MATCH,

    INVALID_TRANSITION;

    /**
     * Create a transition from a String (as they are in the input file)
     * @param transitionString The string
     * @return The HMMInnerTransition corresponding to that string, or INVALID_TRANSITION if it's invalid
     */
    public static HMMInnerTransition fromString(String transitionString) {
        switch (transitionString) {
            case "MM":
                return MATCH_MATCH;
            case "MI":
                return MATCH_INSERT;
            case "MD":
                return MATCH_DELETE;
            case "II":
                return INSERT_INSERT;
            case "IM":
                return INSERT_MATCH;
            case "DD":
                return DELETE_DELETE;
            case "DM":
                return DELETE_MATCH;
            default:
                return INVALID_TRANSITION;
        }
    }
}

package be.robbevanherck.javafraggenescan.enums;

/**
 * The possible state transitions
 */
public enum StateTransition {
    // Inside gene regions
    MATCH_MATCH("MM"),
    MATCH_INSERT("MI"),
    MATCH_DELETE("MD"),
    INSERT_INSERT("II"),
    INSERT_MATCH("IM"),
    DELETE_DELETE("DD"),
    DELETE_MATCH("DM"),

    // Outside gene regions
    GENE_END("GE"),
    GENE_GENE("GG"),
    END_NONMATCHING("ER"),
    NONMATCHING_START("RS"),
    NONMATCHING_NONMATCHING("RR"),
    END_START_SAME_DIRECTION("ES"),
    END_START_CROSSOVER("ES1");

    private final String text;

    StateTransition(String text) {
        this.text = text;
    }

    /**
     * The number of state transitions
     */
    public static final int NUM_STATE_TRANSITIONS = StateTransition.values().length;

    /**
     * Returns a StateTransition from the string representing it
     * @param text The string
     * @return The StateTransition corresponding to that string
     */
    public static StateTransition fromString(String text) {
        for (StateTransition b : StateTransition.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No StateTransition from text " + text + " found") ;
    }
}

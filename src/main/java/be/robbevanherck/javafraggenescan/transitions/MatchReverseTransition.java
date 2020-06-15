package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Represents a transition to a reverse M state
 */
public class MatchReverseTransition extends MatchTransition {
    /**
     * Create a new MatchReverseTransition
     *
     * @param toState The state to which this transition goes
     */
    public MatchReverseTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public float calculateProbability() {
        //TODO
        return 0;
    }
}

package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

/**
 * Represents a transition to an S or S' state
 */
public abstract class StartTransition extends StartEndTransition {
    /**
     * Create a new StartTransition
     *
     * @param toState The state to which this transition goes
     */
    StartTransition(HMMState toState) {
        super(toState);
    }

    @Override
    protected PathProbability getIncomingProbability(ViterbiStep currStep) {
        return PathProbability.max(
                getProbabilityFromNonCodingState(currStep),
                getProbabilityFromForwardEndState(currStep),
                getProbabilityFromReverseEndState(currStep)
        );
    }

    /**
     * Get the probability to go from an E' state to the S state
     * @param currStep The current Viterbi step
     * @return The probability
     */
    protected abstract PathProbability getProbabilityFromReverseEndState(ViterbiStep currStep);

    /**
     * Get the probability to go from an E state to the S state
     * @param currStep The current Viterbi step
     * @return The probability
     */
    protected abstract PathProbability getProbabilityFromForwardEndState(ViterbiStep currStep);

    /**
     * Get the probability to go from an R state to the S state
     * @param currStep The current Viterbi step
     * @return The probability
     */
    protected abstract PathProbability getProbabilityFromNonCodingState(ViterbiStep currStep);
}

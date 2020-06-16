package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to a I (forward or reverse) state
 */
public abstract class InsertTransition extends Transition {
    /**
     * Create a new InsertTransition
     *
     * @param toState The state to which this transition goes
     */
    InsertTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public PathProbability calculatePathProbability(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        return PathProbability.max(
                getProbabilityFromInsertion(parameters, previous, currentStep),
                getProbabilityFromMatch(parameters, previous, currentStep)
        );
    }

    /**
     * Calculate the probability of staying in the same I state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param currentStep The current Viterbi step
     * @return The probability
     */
    protected PathProbability getProbabilityFromInsertion(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        double probability = previous.getProbabilityFor(toState) *                                                              // Probability to be in a Ix state at t-1
                                    parameters.getInnerTransitionProbability(HMMInnerTransition.INSERT_INSERT) *                // Probability for a transition I -> I
                                    parameters.getInsertInsertEmissionProbability(previous.getInput(), currentStep.getInput()); // Probability for an emission for I -> I
        return new PathProbability(toState, probability);
    }

    /**
     * Calculate the probability of staying in the going from an M state to the I state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param currentStep The current Viterbi step
     * @return The probability
     */
    protected PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        HMMState correspondingMatchingState = HMMState.matchingStateForInsert(toState);
        double probability =  previous.getProbabilityFor(correspondingMatchingState) *                                          // Probability to be in a Mx state at t-1
                                    parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_INSERT) *                 // Probability for a transition M -> I
                                    parameters.getMatchInsertEmissionProbability(previous.getInput(), currentStep.getInput());  // Probability for an emission for I -> I
        return new PathProbability(correspondingMatchingState, probability);
    }
}

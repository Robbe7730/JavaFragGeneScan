package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.HMMInnerTransition;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

/**
 * Represents a transition to a forward I state
 */
public class InsertForwardTransition extends InsertTransition {
    /**
     * Create a new InsertForwardTransition
     *
     * @param toState The state to which this transition goes
     */
    public InsertForwardTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();
        HMMParameters parameters = currentStep.getParameters();

        return Math.max(
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
    protected double getProbabilityFromInsertion(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        return  previous.getValueFor(toState) *                                                             // Probability to be in a Ix state at t-1
                parameters.getInnerTransitionProbability(HMMInnerTransition.INSERT_INSERT) *                // Probability for a transition I -> I
                parameters.getInsertInsertEmissionProbability(previous.getInput(), currentStep.getInput()); // Probability for an emission for I -> I
    }

    /**
     * Calculate the probability of staying in the going from an M state to the I state
     * @param parameters The parameters for HMM
     * @param previous The previous Viterbi step
     * @param currentStep The current Viterbi step
     * @return The probability
     */
    protected double getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, ViterbiStep currentStep) {
        HMMState correspondingMatchingState = HMMState.matchingStateForInsert(toState);
        return  previous.getValueFor(correspondingMatchingState) *                                          // Probability to be in a Mx state at t-1
                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_INSERT) *                 // Probability for a transition M -> I
                parameters.getMatchInsertEmissionProbability(previous.getInput(), currentStep.getInput());  // Probability for an emission for I -> I
    }
}

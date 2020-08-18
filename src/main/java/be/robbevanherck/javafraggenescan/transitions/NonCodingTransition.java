package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the R state
 */
public class NonCodingTransition extends Transition {
    /**
     * Create a new NonCodingTransition
     */
    public NonCodingTransition() {
        super(HMMState.NON_CODING);
    }

    @Override
    public PathProbability calculatePathProbability(ViterbiStep currentStep) {
        HMMParameters parameters = currentStep.getParameters();
        ViterbiStep previous = currentStep.getPrevious();

        /* FROM R STATE */

        double probability = previous.getProbabilityFor(HMMState.NON_CODING) *                                          // Probability to be in R state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.NONCODING_NONCODING) *                      // Probability of outer transition R -> R
                parameters.getNonCodingNonCodingEmissionProbability(previous.getInput(), currentStep.getInput());       // Probability of emission of R

        PathProbability bestValue = new PathProbability(HMMState.NON_CODING, probability);

        /* FROM E STATE */

        probability = previous.getProbabilityFor(HMMState.END) *                                // Probability to be in E state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.END_NONCODING);     // Probability of outer transition E -> R
        bestValue = PathProbability.max(bestValue,
                new PathProbability(HMMState.END, probability)
        );

        /* FROM E' STATE */

        probability = previous.getProbabilityFor(HMMState.END_REVERSE) *                        // Probability to be in E' state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.END_NONCODING);     // Probability of outer transition E -> R
        bestValue = PathProbability.max(bestValue,
                new PathProbability(HMMState.END_REVERSE, probability)
        );

        // The code multiplies by 0.95, but that decreases sensitivity and precision, so I've left it out

        return bestValue;
    }
}

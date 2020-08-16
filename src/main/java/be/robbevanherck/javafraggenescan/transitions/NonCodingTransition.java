package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

import java.math.BigDecimal;

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

        BigDecimal probability = previous.getProbabilityFor(HMMState.NON_CODING).multiply(                              // Probability to be in R state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.NONCODING_NONCODING)).multiply(             // Probability of outer transition R -> R
                parameters.getNonCodingNonCodingEmissionProbability(previous.getInput(), currentStep.getInput()));      // Probability of emission of R

        PathProbability bestValue = new PathProbability(HMMState.NON_CODING, probability);

        /* FROM E STATE */

        probability = previous.getProbabilityFor(HMMState.END).multiply(                        // Probability to be in E state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.END_NONCODING));    // Probability of outer transition E -> R
        bestValue = PathProbability.max(bestValue,
                new PathProbability(HMMState.END, probability)
        );

        /* FROM E STATE */

        probability = previous.getProbabilityFor(HMMState.END_REVERSE).multiply(                // Probability to be in E' state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.END_NONCODING));    // Probability of outer transition E -> R
        bestValue = PathProbability.max(bestValue,
                new PathProbability(HMMState.END_REVERSE, probability)
        );

        return bestValue;
    }
}

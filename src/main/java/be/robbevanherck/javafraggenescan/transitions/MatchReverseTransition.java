package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.*;

import java.math.BigDecimal;

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
    protected boolean isCorrectStopCodon(Triple<AminoAcid> tripleToCheck) {
        return StartStopUtil.isReverseStopCodon(tripleToCheck);
    }

    @Override
    protected PathProbability getProbabilityFromStart(ViterbiStep currentStep, Triple<AminoAcid> codonEndingAtT) {
        ViterbiStep previous = currentStep.getPrevious();
        if (canComeFromStartState(currentStep)) {
            BigDecimal probability = previous.getProbabilityFor(HMMState.START_REVERSE).multiply(                        // Probability to be in the Start' state at t-1
                            currentStep.getParameters().getReverseMatchEmissionProbability(toState, codonEndingAtT));    // Probability of emission from M' state (transition is guaranteed)
            return new PathProbability(HMMState.START, probability);
        }
        return new PathProbability(HMMState.START, BigDecimal.ZERO);
    }

    protected boolean canComeFromStartState(ViterbiStep currentStep) {
        // To be coming from a start state, we need to check the reverse of an end state
        return (toState == HMMState.INSERT_REVERSE_1 || toState == HMMState.INSERT_REVERSE_4) &&
                (isCorrectStopCodon(new Triple<>(
                        currentStep.getInput(),
                        currentStep.getPrevious().getInput(),
                        currentStep.getPrevious().getPrevious().getInput()
                )));
    }

    @Override
    protected PathProbability getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        PathProbability bestValue = new PathProbability(HMMState.NO_STATE, BigDecimal.ZERO);
        if (!parameters.wholeGenome()) {
            HMMState currState = HMMState.previousState(toState);
            int numD = 1;
            while (currState != toState) {
                BigDecimal probability = previous.getProbabilityFor(currState).multiply(                                                                            // Probability to be in currState at t-1
                        parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_DELETE)).multiply(BigDecimal.valueOf(0.25)).multiply(                     // Probability of transition + emission of M -> D
                        ((parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_DELETE).multiply(BigDecimal.valueOf(0.25))).pow(numD))).multiply(      // Probability of numD transitions and emissions of D -> D
                        parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_MATCH)).multiply(                                                        // Probability of transition of M -> D
                        parameters.getReverseMatchEmissionProbability(currState, codonEndingAtT));                                                                  // Probability of emission of M
                bestValue = PathProbability.max(bestValue, new PathProbability(currState, probability));

                numD++;
                currState = HMMState.previousState(currState);
            }
        }
        return bestValue;
    }

    @Override
    protected PathProbability getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        HMMState previousMatch = HMMState.previousState(toState);
        BigDecimal probability = previous.getProbabilityFor(previousMatch).multiply(                                                // Probability to be in the M' state at t-1
                                            parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_MATCH)).multiply(     // Probability of inner transition M -> M
                                            parameters.getReverseMatchEmissionProbability(toState, codonEndingAtT));                // Probability of emission from M' sate
        return new PathProbability(previousMatch, probability);
    }
}

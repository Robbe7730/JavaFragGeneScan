package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to a forward M state, except the M1 state
 */
public class MatchForwardTransition extends MatchTransition {
    /**
     * Create a new MatchForwardTransition
     *
     * @param toState The state to which this transition goes
     */
    public MatchForwardTransition(HMMState toState) {
        super(toState);
    }

    @Override
    protected boolean isStopCodon(Triple<AminoAcid> codonWithoutInsertions) {
        return StartStopUtil.isForwardStopCodon(codonWithoutInsertions);
    }

    @Override
    protected double getProbabilityFromMatch(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        return previous.getValueFor(HMMState.previousState(toState)) *                      // Probability to be in state previousMState at t-1
                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_MATCH) *  // Probability for transition M -> M
                parameters.getMatchEmissionProbability(toState, codonEndingAtT);            // Probability for an emission of M
    }

    @Override
    protected double getProbabilityThroughDeletions(HMMParameters parameters, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        double bestValue = 0;
        if (!parameters.wholeGenome()) {
            HMMState currState = HMMState.previousState(toState);
            int numD = 1;
            while (currState != toState) {
                bestValue = Math.max(bestValue,
                        previous.getValueFor(currState) *                                                                               // Probability to be in currState at t-1
                                parameters.getInnerTransitionProbability(HMMInnerTransition.MATCH_DELETE) * 0.25 *                      // Probability of transition + emission of M -> D
                                Math.pow((parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_DELETE) * 0.25), numD) *   // Probability of numD transitions and emissions of D -> D
                                parameters.getInnerTransitionProbability(HMMInnerTransition.DELETE_MATCH) *                             // Probability of transition of M -> D
                                parameters.getMatchEmissionProbability(currState, codonEndingAtT)                                       // Probability of emission of M
                );

                numD++;
                currState = HMMState.previousState(currState);
            }
        }
        return bestValue;
    }

    @Override
    protected double getProbabilityFromStart(ViterbiStep currentStep, ViterbiStep previous, Triple<AminoAcid> codonEndingAtT) {
        // As M1 is a separate class, this is always 0
        return 0;
    }

}

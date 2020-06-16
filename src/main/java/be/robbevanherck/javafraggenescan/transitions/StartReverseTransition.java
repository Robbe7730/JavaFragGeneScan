package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the S' state
 */
public class StartReverseTransition extends StartTransition {
    /**
     * Create a new EndTransition
     */
    public StartReverseTransition() {
        super(HMMState.START_REVERSE);
    }

    @Override
    protected boolean isStartStopCodon(Triple<AminoAcid> codonEndingAtT) {
        return StartStopUtil.isReverseStartCodon(codonEndingAtT);
    }

    @Override
    protected double getCodonDependantProbability(Triple<AminoAcid> codon) {
        // TODO these are values from the paper, the original code uses different values!!
        // The first amino-acid is always A, so we don't need to check that one
        if (codon.getSecondValue() == AminoAcid.T && codon.getThirdValue() == AminoAcid.C) {
            return 0.54;
        } else if (codon.getSecondValue() == AminoAcid.T && codon.getThirdValue() == AminoAcid.T) {
            return 0.30;
        } else if (codon.getSecondValue() == AminoAcid.C && codon.getThirdValue() == AminoAcid.T) {
            return 0.16;
        } else {
            return 0;
        }
    }

    @Override
    protected double getGaussianProbability(ViterbiStep currStep) {
        return 0;
    }

    @Override
    protected double getProbabilityFromReverseEndState(ViterbiStep currStep) {
        return currStep.getPrevious().getValueFor(HMMState.END_REVERSE) *                                       // Probability to be in an E' state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.END_START_SAME);      // Probability for a transition E' -> S'
    }

    @Override
    protected double getProbabilityFromForwardEndState(ViterbiStep currStep) {
        return currStep.getPrevious().getValueFor(HMMState.END) *                                               // Probability to be in an E state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.END_START_REVERSE);   // Probability for a transition E -> S'
    }

    @Override
    protected double getProbabilityFromNonCodingState(ViterbiStep currStep) {
        return currStep.getPrevious().getValueFor(HMMState.NON_CODING) *                                        // Probability to be in an R state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.NONCODING_START);     // Probability for a transition R -> S'
    }
}

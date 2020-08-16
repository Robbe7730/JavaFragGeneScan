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
        // The last amino-acid is always A, so we don't need to check that one
        if (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.C) {
            return 0.54;
        } else if (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.T) {
            return 0.30;
        } else if (codon.getFirstValue() == AminoAcid.C && codon.getSecondValue() == AminoAcid.T) {
            return 0.16;
        } else {
            return 0;
        }
    }

    @Override
    protected double getGaussianProbability(ViterbiStep currStep) {
        HMMParameters parameters = currStep.getParameters();

        int nucleotidesChecked = 0;
        double tempProduct = 1;

        Triple<AminoAcid> codon = getCodonStartingAtX(currStep, nucleotidesChecked);

        // TODO I'm not sure why this is 58 instead of 61, but I followed the original code
        // Read from the PWM until we reach te beginning or the end of our window
        while (codon != null && nucleotidesChecked <= 58) {
            tempProduct *= parameters.getReverseStartPWMProbability(58 - nucleotidesChecked, codon);

            nucleotidesChecked++;
            codon = getCodonStartingAtX(currStep, nucleotidesChecked);
        }

        // Avoid divide by zero
        double startFrequency = (nucleotidesChecked == 0) ? 0 : tempProduct * (58.0 / nucleotidesChecked);

        return calculateStatisticalProbability(parameters, startFrequency);
    }

    @Override
    protected PathProbability getProbabilityFromReverseEndState(ViterbiStep currStep) {
        double probability = currStep.getPrevious().getProbabilityFor(HMMState.END_REVERSE) *                   // Probability to be in an E' state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.END_START_SAME);      // Probability for a transition E' -> S'
        return new PathProbability(HMMState.END_REVERSE, probability);
    }

    @Override
    protected PathProbability getProbabilityFromForwardEndState(ViterbiStep currStep) {
        double probability = currStep.getPrevious().getProbabilityFor(HMMState.END) *                           // Probability to be in an E state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.END_START_REVERSE);   // Probability for a transition E -> S'
        return new PathProbability(HMMState.END, probability);
    }

    @Override
    protected PathProbability getProbabilityFromNonCodingState(ViterbiStep currStep) {
        double probability = currStep.getPrevious().getProbabilityFor(HMMState.NON_CODING) *                    // Probability to be in an R state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.NONCODING_START);     // Probability for a transition R -> S'
        return new PathProbability(HMMState.NON_CODING, probability);
    }
}

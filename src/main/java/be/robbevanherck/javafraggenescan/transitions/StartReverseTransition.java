package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.*;

import java.math.BigDecimal;

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
    protected BigDecimal getCodonDependantProbability(Triple<AminoAcid> codon) {
        // TODO these are values from the paper, the original code uses different values!!
        // The last amino-acid is always A, so we don't need to check that one
        if (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.C) {
            return BigDecimal.valueOf(0.54);
        } else if (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.T) {
            return BigDecimal.valueOf(0.30);
        } else if (codon.getFirstValue() == AminoAcid.C && codon.getSecondValue() == AminoAcid.T) {
            return BigDecimal.valueOf(0.16);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    protected BigDecimal getGaussianProbability(ViterbiStep currStep) {
        HMMParameters parameters = currStep.getParameters();

        int nucleotidesChecked = 0;
        BigDecimal tempProduct = BigDecimal.ONE;

        Triple<AminoAcid> codon = getCodonStartingAtX(currStep, nucleotidesChecked);

        // TODO I'm not sure why this is 58 instead of 61, but I followed the original code
        // Read from the PWM until we reach te beginning or the end of our window
        while (codon != null && nucleotidesChecked <= 58) {
            tempProduct = tempProduct.multiply(parameters.getReverseStartPWMProbability(58 - nucleotidesChecked, codon));

            nucleotidesChecked++;
            codon = getCodonStartingAtX(currStep, nucleotidesChecked);
        }

        // Avoid divide by zero
        BigDecimal startFrequency = (nucleotidesChecked == 0) ? BigDecimal.ZERO : tempProduct.multiply(BigDecimal.valueOf(58.0 / nucleotidesChecked));

        return calculateStatisticalProbability(parameters, startFrequency);
    }

    @Override
    protected PathProbability getProbabilityFromReverseEndState(ViterbiStep currStep) {
        BigDecimal probability = currStep.getPrevious().getProbabilityFor(HMMState.END_REVERSE).multiply(           // Probability to be in an E' state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.END_START_SAME));         // Probability for a transition E' -> S'
        return new PathProbability(HMMState.END_REVERSE, probability);
    }

    @Override
    protected PathProbability getProbabilityFromForwardEndState(ViterbiStep currStep) {
        BigDecimal probability = currStep.getPrevious().getProbabilityFor(HMMState.END).multiply(                   // Probability to be in an E state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.END_START_REVERSE));      // Probability for a transition E -> S'
        return new PathProbability(HMMState.END, probability);
    }

    @Override
    protected PathProbability getProbabilityFromNonCodingState(ViterbiStep currStep) {
        BigDecimal probability = currStep.getPrevious().getProbabilityFor(HMMState.NON_CODING).multiply(            // Probability to be in an R state at t-1
                currStep.getParameters().getOuterTransitionProbability(HMMOuterTransition.NONCODING_START));        // Probability for a transition R -> S'
        return new PathProbability(HMMState.NON_CODING, probability);
    }
}

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
        HMMParameters parameters = currStep.getParameters();

        // Make sure the previous steps exist
        if (currStep.getPrevious().getPrevious() == null) {
            return 0;
        }
        ViterbiStep firstStep = currStep;
        ViterbiStep secondStep = firstStep.getPrevious();
        ViterbiStep thirdStep = secondStep.getPrevious();

        int nucleotidesChecked = 1;
        double tempProduct = parameters.getReverseStartPWMProbability(58, new Triple<>(
                firstStep.getInput(),
                secondStep.getInput(),
                thirdStep.getInput()
        ));

        // TODO I'm not sure why this is 58 instead of 61, but I followed the original code
        // Read from the PWM until we reach te beginning or the end of our window
        while (thirdStep != null && nucleotidesChecked <= 58) {
            tempProduct *= parameters.getReverseStartPWMProbability(58 - nucleotidesChecked, new Triple<>(
                    firstStep.getInput(),
                    secondStep.getInput(),
                    thirdStep.getInput()
            ));

            firstStep = secondStep;
            secondStep = thirdStep;
            thirdStep = thirdStep.getPrevious();

            nucleotidesChecked++;
        }

        double startFrequency = tempProduct * (58.0 / nucleotidesChecked);

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

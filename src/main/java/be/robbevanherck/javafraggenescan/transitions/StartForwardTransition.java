package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents the transition to the S state
 */
public class StartForwardTransition extends StartTransition {
    /**
     * Create a new StartForwardTransition
     */
    public StartForwardTransition() {
        super(HMMState.START);
    }

    @Override
    protected boolean isStartStopCodon(Triple<AminoAcid> codonEndingAtT) {
        return StartStopUtil.isForwardStopCodon(codonEndingAtT);
    }

    @Override
    protected double getCodonDependantProbability(Triple<AminoAcid> codon) {
        // The last 2 amino-acids are always TG, so we only need to check the first value
        if (codon.getFirstValue() == AminoAcid.A) {
            return 0.83;
        } else if (codon.getFirstValue() == AminoAcid.G) {
            return 0.10;
        } else if (codon.getFirstValue() == AminoAcid.T) {
            return 0.07;
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
        double tempProduct = parameters.getForwardStartPWMProbability(58, new Triple<>(
                firstStep.getInput(),
                secondStep.getInput(),
                thirdStep.getInput()
        ));

        // Instead of going from -30 to 30, I first go from 0 to -30 (using previous) and then from 0 to 30 (using nextValues)

        while (thirdStep != null && nucleotidesChecked < 30) {
            tempProduct *= parameters.getForwardStartPWMProbability(29 - nucleotidesChecked, new Triple<>(
                    firstStep.getInput(),
                    secondStep.getInput(),
                    thirdStep.getInput()
            ));

            firstStep = secondStep;
            secondStep = thirdStep;
            thirdStep = thirdStep.getPrevious();

            nucleotidesChecked++;
        }

        AminoAcid firstValue = currStep.getPrevious().getInput();
        AminoAcid secondValue = currStep.getInput();
        AminoAcid thirdValue = currStep.getNextInput();

        for(int i = 0; i < 31 && (i+2) < currStep.getNextValues().size(); i++) {
            tempProduct *= parameters.getForwardStartPWMProbability(nucleotidesChecked, new Triple<>(
                    firstValue,
                    secondValue,
                    thirdValue
            ));

            firstValue = secondValue;
            secondValue = thirdValue;
            thirdValue = currStep.getNextValues().get(i+2);

            nucleotidesChecked++;
        }

        double startFrequency = tempProduct * (58.0 / nucleotidesChecked);

        return calculateStatisticalProbability(parameters, startFrequency);
    }

    @Override
    protected PathProbability getProbabilityFromReverseEndState(ViterbiStep currStep) {
        HMMParameters parameters = currStep.getParameters();
        double probability = currStep.getPrevious().getProbabilityFor(HMMState.END_REVERSE) *       // Probability to be in E' state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.END_START_REVERSE);     // Probability for an outer transition E' -> S
        return new PathProbability(HMMState.END_REVERSE, probability);
    }

    @Override
    protected PathProbability getProbabilityFromForwardEndState(ViterbiStep currStep) {
        HMMParameters parameters = currStep.getParameters();
        double probability = currStep.getPrevious().getProbabilityFor(HMMState.END) *           // Probability to be in E state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.END_START_SAME);    // Probability for an outer transition E -> S
        return new PathProbability(HMMState.END, probability);
    }

    @Override
    protected PathProbability getProbabilityFromNonCodingState(ViterbiStep currStep) {
        HMMParameters parameters = currStep.getParameters();
        double probability = currStep.getPrevious().getProbabilityFor(HMMState.NON_CODING) *     // Probability to be in R state at t-1
                parameters.getOuterTransitionProbability(HMMOuterTransition.NONCODING_START);    // Probability for an outer transition R -> S
        return new PathProbability(HMMState.NON_CODING, probability);
    }
}

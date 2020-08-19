package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the E state
 */
public class EndForwardTransition extends EndTransition {
    /**
     * Create a new EndForwardTransition
     */
    public EndForwardTransition() {
        super(HMMState.END);
    }


    @Override
    protected boolean isStartStopCodon(Triple<AminoAcid> codonEndingAtT) {
        return StartStopUtil.isForwardStopCodon(codonEndingAtT);
    }

    @Override
    protected PathProbability getIncomingProbability(ViterbiStep currStep) {
        return PathProbability.max(
                getProbabilityFromMatchState(HMMState.MATCH_6, currStep.getPrevious()),
                getProbabilityFromMatchState(HMMState.MATCH_3, currStep.getPrevious())
        );
    }

    @Override
    protected double getCodonDependantProbability(Triple<AminoAcid> codon) {
        // DIFFERENCE these are values from the paper, the original code uses different values!!
        // The first amino-acid is always T, no need to check that one
        if (codon.getSecondValue() == AminoAcid.A && codon.getThirdValue() == AminoAcid.G) {
            return 0.54;
        } else if (codon.getSecondValue() == AminoAcid.A && codon.getThirdValue() == AminoAcid.A) {
            return 0.33;
        } else if (codon.getSecondValue() == AminoAcid.G && codon.getThirdValue() == AminoAcid.A) {
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
        double tempProduct = parameters.getForwardEndPWMProbability(58, new Triple<>(
                firstStep.getInput(),
                secondStep.getInput(),
                thirdStep.getInput()
        ));

        // DIFFERENCE I'm not sure why this is 58 instead of 61, but I followed the original code
        // Read from the PWM until we reach te beginning or the end of our window
        while (thirdStep != null && nucleotidesChecked <= 58) {
            tempProduct *= parameters.getForwardEndPWMProbability(58 - nucleotidesChecked, new Triple<>(
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
    protected void overrideFutureValues(ViterbiStep currStep, PathProbability pathProbability) {
        super.overrideFutureValues(currStep, pathProbability);

        // It is also not possible to have 3 exact matches for this codon, so we override them here
        currStep.setValueFor(HMMState.MATCH_1, new PathProbability(HMMState.NO_STATE, 0));
        currStep.setValueFor(HMMState.MATCH_2, new PathProbability(HMMState.NO_STATE, 0), 1);
        currStep.setValueFor(HMMState.MATCH_3, new PathProbability(HMMState.NO_STATE, 0), 2);
        currStep.setValueFor(HMMState.MATCH_4, new PathProbability(HMMState.NO_STATE, 0));
        currStep.setValueFor(HMMState.MATCH_5, new PathProbability(HMMState.NO_STATE, 0), 1);
        currStep.setValueFor(HMMState.MATCH_6, new PathProbability(HMMState.NO_STATE, 0), 2);
    }
}

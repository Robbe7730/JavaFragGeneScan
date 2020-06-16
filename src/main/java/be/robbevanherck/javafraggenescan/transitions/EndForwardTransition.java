package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

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
    protected double getIncomingProbability(ViterbiStep currStep) {
        return Math.max(
                getProbabilityFromMatchState(HMMState.MATCH_6, currStep.getPrevious()),
                getProbabilityFromMatchState(HMMState.MATCH_3, currStep.getPrevious())
        );
    }

    @Override
    protected double getCodonDependantProbability(Triple<AminoAcid> codon) {
        // TODO these are values from the paper, the original code uses different values!!
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
        return 0;
    }
}

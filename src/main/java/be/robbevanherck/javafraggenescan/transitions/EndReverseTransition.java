package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

/**
 * Represents a transition to the E' state
 */
public class EndReverseTransition extends EndTransition {
    /**
     * Create a new EndReverseTransition
     */
    public EndReverseTransition() {
        super(HMMState.END_REVERSE);
    }

    @Override
    protected boolean isStartStopCodon(Triple<AminoAcid> codonEndingAtT) {
        return StartStopUtil.isReverseStopCodon(codonEndingAtT);
    }

    @Override
    protected double getIncomingProbability(ViterbiStep currStep) {
        return getProbabilityFromMatchState(HMMState.MATCH_REVERSE_6, currStep);
    }

    @Override
    protected double getCodonDependantProbability(Triple<AminoAcid> codon) {
        // The first 2 amino-acids are always CA, so we only need to check the third value
        if (codon.getThirdValue() == AminoAcid.T) {
            return 0.83;
        } else if (codon.getThirdValue() == AminoAcid.C) {
            return 0.10;
        } else if (codon.getThirdValue() == AminoAcid.A) {
            return 0.07;
        } else {
            return 0;
        }
    }

    @Override
    protected double getGaussianProbability(ViterbiStep currStep) {
        return 0;
    }
}

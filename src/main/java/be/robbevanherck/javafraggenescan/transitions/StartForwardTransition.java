package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.StartStopUtil;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;

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
        return 0;
    }

    @Override
    protected double getProbabilityFromReverseEndState(ViterbiStep currStep) {
        return 0;
    }

    @Override
    protected double getProbabilityFromForwardEndState(ViterbiStep currStep) {
        return 0;
    }

    @Override
    protected double getProbabilityFromNonCodingState(ViterbiStep currStep) {
        return 0;
    }
}

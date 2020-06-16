package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import org.sonatype.inject.Nullable;

/**
 * Represents a transition to an S or E state, forward or reverse
 */
public abstract class StartEndTransition extends Transition {
    /**
     * Create a new StartEndTransition
     *
     * @param toState The state to which this transition goes
     */
    StartEndTransition(HMMState toState) {
        super(toState);
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {
        if (isStartStopCodon(getCodonEndingAtT(currentStep))) {
            // All start and stop probabilities are made of these probabilities
            return getIncomingProbability(currentStep) *
                    getCodonDependantProbability(getCodonEndingAtT(currentStep)) *
                    getGaussianProbability(currentStep);
        }
        return 0;
    }

    /**
     * Returns whether or not the given codon is the correct type of start/stop codon
     * @param codonEndingAtT The codon
     * @return true if it matches, false otherwise
     */
    protected abstract boolean isStartStopCodon(@Nullable Triple<AminoAcid> codonEndingAtT);

    /**
     * Get the probability of going to this Start/End state
     * @param currStep The current Viterbi Step
     * @return The probability
     */
    protected abstract double getIncomingProbability(ViterbiStep currStep);

    /**
     * Get the extra probability based on the current codon
     * @param codon The codon we found
     * @return The probability
     */
    protected abstract double getCodonDependantProbability(Triple<AminoAcid> codon);

    /**
     * Get the probability based on the statistical properties
     * @param currStep The current Viterbi Step
     * @return The probability
     */
    protected abstract double getGaussianProbability(ViterbiStep currStep);
}

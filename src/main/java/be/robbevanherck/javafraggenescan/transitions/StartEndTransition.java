package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;
import be.robbevanherck.javafraggenescan.repositories.GaussianArgumentsRepository;
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
    public void calculateStateTransition(ViterbiStep currentStep) {
        if (isStartStopCodon(getCodonStartingAtT(currentStep))) {
            overrideFutureValues(currentStep);
            // We calculate the probability for t+2, when the start/stop codon ends
            currentStep.setValueFor(toState, calculateProbability(currentStep), 2);
        } else {
            currentStep.setValueFor(toState, 0);
        }
    }

    @Override
    public double calculateProbability(ViterbiStep currentStep) {

        // All start and stop probabilities are made of these probabilities
        return getIncomingProbability(currentStep) *
                getCodonDependantProbability(getCodonEndingAtT(currentStep)) *
                getGaussianProbability(currentStep);
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

    /**
     * Calculate the statistical value I don't understand
     * @param parameters The HMM parameters
     * @param startFrequency The input calculated before this step
     * @return The probability
     */
    protected double calculateStatisticalProbability(HMMParameters parameters, double startFrequency) {
        // Get all the arguments ready
        double sigma = parameters.getGaussianArgument(toState, GaussianArgumentsRepository.GaussianArgument.SIGMA);
        double mu = parameters.getGaussianArgument(toState, GaussianArgumentsRepository.GaussianArgument.MU);
        double alpha = parameters.getGaussianArgument(toState, GaussianArgumentsRepository.GaussianArgument.ALPHA);
        double sigmaR = parameters.getGaussianArgument(toState, GaussianArgumentsRepository.GaussianArgument.SIGMA_R);
        double muR = parameters.getGaussianArgument(toState, GaussianArgumentsRepository.GaussianArgument.MU_R);
        double alphaR = parameters.getGaussianArgument(toState, GaussianArgumentsRepository.GaussianArgument.ALPHA_R);

        // Do some crazy statistics I don't understand
        double eKdN = alpha * Math.exp(-Math.pow(startFrequency - mu, 2) / (2 * Math.pow(sigma, 2)));
        double eKdR = alphaR * Math.exp(-Math.pow(startFrequency - muR, 2) / (2 * Math.pow(sigmaR, 2)));

        return Math.max(Math.min(eKdN / (eKdN + eKdR), 0.99), 0.01);
    }

    /**
     * Used to override future values (mainly t+1 and t+2)
     * @param currStep The current Viterbi step
     */
    protected void overrideFutureValues(ViterbiStep currStep) {
        // It is impossible to come from the non-last ending position, so set these values to 0
        currStep.setValueFor(toState, 0);
        currStep.setValueFor(toState, 0, 1);


        // TODO why not block M states from other states?
    }
}

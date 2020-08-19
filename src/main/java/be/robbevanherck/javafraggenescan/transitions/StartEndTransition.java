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
        if (isStartStopCodon(getCodonStartingOrEndingAtT(currentStep))) {
            PathProbability pathProbability = calculatePathProbability(currentStep);
            overrideFutureValues(currentStep, pathProbability);
        } else {
            currentStep.setValueFor(toState, new PathProbability(HMMState.NO_STATE, 0));
        }
    }

    @Override
    public PathProbability calculatePathProbability(ViterbiStep currentStep) {
        // Find the probability to arrive in the Start/End state
        PathProbability pathProbability = getIncomingProbability(currentStep);

        // Find the codon that can be a start codon
        Triple<AminoAcid> codonAtT = getCodonStartingOrEndingAtT(currentStep);

        double probability = 0;
        if (codonAtT != null) {
            // If the codon is a start/stop codon calculate the extra probabilities
            probability = pathProbability.getProbability() *
                    getCodonDependantProbability(codonAtT) *
                    getGaussianProbability(currentStep);
        }
        pathProbability.setProbability(probability);
        return pathProbability;
    }

    protected abstract Triple<AminoAcid> getCodonStartingOrEndingAtT(ViterbiStep currentStep);

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
    protected abstract PathProbability getIncomingProbability(ViterbiStep currStep);

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
     * @param currentStep The current Viterbi step
     * @param pathProbability The pre-calculated path-probability combination
     */
    protected void overrideFutureValues(ViterbiStep currentStep, PathProbability pathProbability) {
        // We calculate the probability for t+2 and path for t, so this needs to be split up
        currentStep.setValueFor(toState, new PathProbability(pathProbability.getPreviousState(), 0));
        currentStep.setValueFor(toState, new PathProbability(toState, 0), 1);
        currentStep.setValueFor(toState, new PathProbability(toState, pathProbability.getProbability()), 2);
    }
}

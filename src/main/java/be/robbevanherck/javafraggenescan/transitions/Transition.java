package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Triple;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * The top-level interface for transition handlers
 */
public abstract class Transition {
    protected final HMMState toState;

    /**
     * Create a new TransitionHandler
     * @param toState The state to which this transition goes
     */
    Transition(HMMState toState) {
        this.toState = toState;
    }

    /**
     * Calculate a step in the Viterbi algorithm, altering the current ViterbiStep
     * @param curr The current step in the Viterbi algorithm, to be filled in
     */
    public void calculateStateTransition(ViterbiStep curr) {
        curr.setValueFor(this.toState, calculateProbability(curr));
    }

    /**
     * Returns what state this transition goes to
     * @return The state this transition goes to
     */
    public HMMState getToState() {
        return toState;
    }

    /**
     * Calculate the probability of this transition
     * @return The probability
     * @param currentStep The current step
     */
    public abstract double calculateProbability(ViterbiStep currentStep);

    /**
     * Get the codon starting at t-2 and ending at t
     * @param currentStep The current Viterbi Step
     * @return The triple of amino-acids
     */
    protected Triple<AminoAcid> getCodonEndingAtT(ViterbiStep currentStep) {
        ViterbiStep previous = currentStep.getPrevious();

        if (previous.getPrevious() == null) {
            return null;
        }

        return new Triple<>(
                previous.getPrevious().getInput(),
                previous.getInput(),
                currentStep.getInput()
        );
    }

    /**
     * Get the codon starting at t and ending at t+2
     * @param currentStep The current Viterbi Step
     * @return The triple of amino-acids
     */
    protected Triple<AminoAcid> getCodonStartingAtT(ViterbiStep currentStep) {
        if (currentStep.getNextValues().size() < 3) {
            return null;
        }

        return new Triple<>(
                currentStep.getInput(),
                currentStep.getNextValues().get(1),
                currentStep.getNextValues().get(2)
        );
    }
}

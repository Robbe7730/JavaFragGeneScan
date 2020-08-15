package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * The top-level interface for transitions
 */
public abstract class Transition {
    protected final HMMState toState;

    /**
     * Create a new Transition
     * @param toState The state to which this transition goes
     */
    public Transition(HMMState toState) {
        this.toState = toState;
    }

    /**
     * Calculate a step in the Viterbi algorithm, altering the current ViterbiStep
     * @param curr The current step in the Viterbi algorithm, to be filled in
     */
    public void calculateStateTransition(ViterbiStep curr) {
        curr.setValueFor(this.toState, calculatePathProbability(curr));
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
     * @param currentStep The current step
     * @return The path-probability combination
     */
    public abstract PathProbability calculatePathProbability(ViterbiStep currentStep);

    /**
     * Get the codon starting at t-2 and ending at t. if t == 1, G is used as first amino-acid
     * @param currentStep The current Viterbi Step
     * @return The triple of amino-acids
     */
    protected Triple<AminoAcid> getCodonEndingAtT(ViterbiStep currentStep) {
        // previous will exist and have values because values are only actually calculated
        // when t >= 1. At t = 1 the initial values are used.
        ViterbiStep previous = currentStep.getPrevious();

        AminoAcid firstAcid;

        // TODO: why is this?!
        if (previous.getPrevious() == null) {
            firstAcid = AminoAcid.G;
        } else {
            firstAcid = previous.getPrevious().getInput();
        }

        return new Triple<>(
                firstAcid,
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
        return getCodonStartingAtX(currentStep, 0);
    }

    /**
     * Get the codon starting at t+x and ending at t+x+2
     * @param currentStep The current Viterbi Step
     * @param x The offset
     * @return The triple of amino-acids or null when there are not enough next values
     */
    protected Triple<AminoAcid> getCodonStartingAtX(ViterbiStep currentStep, int x) {
        if (x < 0 || currentStep.getNextValues().size() < x+3) {
            return null;
        }

        return new Triple<>(
                currentStep.getNextValues().get(x),
                currentStep.getNextValues().get(x+1),
                currentStep.getNextValues().get(x+2)
        );
    }
}

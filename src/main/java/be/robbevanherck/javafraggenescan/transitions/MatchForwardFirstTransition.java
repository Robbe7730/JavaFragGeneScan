package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.entities.*;

/**
 * Represents a transition to the M1 state
 */
public class MatchForwardFirstTransition extends MatchForwardTransition {
    /**
     * Create a new MatchForwardFirstTransition
     */
    public MatchForwardFirstTransition() {
        super(HMMState.MATCH_1);
    }

    @Override
    public void calculateStateTransition(HMMParameters params, ViterbiStep curr) {
        //TODO if being blocked by END state

        Triple<AminoAcid> tripleEndingAtT = new Triple<>(curr.getPrevious().getPrevious().getInput(), curr.getPrevious().getInput(), curr.getInput());

        // FROM START STATE
        float bestScore = curr.getPrevious().getValueFor(HMMState.START) *
                params.getMatchEmissionFor(tripleEndingAtT, HMMState.MATCH_1);

        curr.setValueFor(HMMState.MATCH_1, bestScore);
    }
}

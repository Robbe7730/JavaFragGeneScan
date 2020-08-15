package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.transitions.InsertTransition;

public class NonAbstractInsertTransition extends InsertTransition {
    /**
     * Create a new Dummy InsertTransition that isn't abstract.
     */
    public NonAbstractInsertTransition() {
        super(HMMState.NO_STATE);
    }

}

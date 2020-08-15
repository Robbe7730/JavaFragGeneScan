package be.robbevanherck.javafraggenescan.transitions;

import be.robbevanherck.javafraggenescan.dummies.DummyHMMParameters;
import be.robbevanherck.javafraggenescan.dummies.DummyViterbiStep;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.PathProbability;
import be.robbevanherck.javafraggenescan.entities.Triple;
import org.junit.Test;

import static be.robbevanherck.javafraggenescan.TestUtil.*;
import static org.junit.Assert.*;

public class MatchForwardTransitionTest {
    MatchForwardTransition matchForwardTransition = new MatchForwardTransition(HMMState.NO_STATE);
    private DummyViterbiStep dummyViterbiStep = new DummyViterbiStep(AminoAcid.A, AminoAcid.T, AminoAcid.G);
    private DummyHMMParameters dummyParameters = new DummyHMMParameters();

    @Test
    public void isCorrectStopCodon() {
        for (Triple<AminoAcid> codon : forwardStopCodons) {
            assertTrue(matchForwardTransition.isCorrectStopCodon(codon));
        }
        for (Triple<AminoAcid> codon : reverseStopCodons) {
            assertFalse(matchForwardTransition.isCorrectStopCodon(codon));
        }
        for (Triple<AminoAcid> codon : forwardStartCodons) {
            assertFalse(matchForwardTransition.isCorrectStopCodon(codon));
        }
        for (Triple<AminoAcid> codon : reverseStartCodons) {
            assertFalse(matchForwardTransition.isCorrectStopCodon(codon));
        }
    }

    @Test
    public void getProbabilityFromMatch() {
        assertEquals(
                // The probabilities are hardcoded into DummyHMMParameters and DummyViterbiStep, but they
                // are unique, so we know it uses the right values
                new PathProbability(HMMState.NO_STATE, 0.7 * 0.1 * 0.5),
                matchForwardTransition.getProbabilityFromMatch(dummyParameters, dummyViterbiStep.getPrevious(), null)
        );
    }

    @Test
    public void getProbabilityThroughDeletions() {
        //TODO: I'm going to rewrite the getXXXTransitions, so testing the current implementation is useless
    }

    @Test
    public void getProbabilityFromStart() {
        //TODO: I'm going to rewrite the getXXXTransitions, so testing the current implementation is useless
    }
}
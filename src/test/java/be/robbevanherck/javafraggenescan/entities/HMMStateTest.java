package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HMMStateTest {

    private final List<HMMState> matchingStates = List.of(
            HMMState.MATCH_1,
            HMMState.MATCH_2,
            HMMState.MATCH_3,
            HMMState.MATCH_4,
            HMMState.MATCH_5,
            HMMState.MATCH_6
    );
    private final List<HMMState> matchingReverseStates = List.of(
            HMMState.MATCH_REVERSE_1,
            HMMState.MATCH_REVERSE_2,
            HMMState.MATCH_REVERSE_3,
            HMMState.MATCH_REVERSE_4,
            HMMState.MATCH_REVERSE_5,
            HMMState.MATCH_REVERSE_6
    );
    private final List<HMMState> insertStates = List.of(
            HMMState.INSERT_1,
            HMMState.INSERT_2,
            HMMState.INSERT_3,
            HMMState.INSERT_4,
            HMMState.INSERT_5,
            HMMState.INSERT_6
    );
    private final List<HMMState> insertReverseStates = List.of(
            HMMState.INSERT_REVERSE_1,
            HMMState.INSERT_REVERSE_2,
            HMMState.INSERT_REVERSE_3,
            HMMState.INSERT_REVERSE_4,
            HMMState.INSERT_REVERSE_5,
            HMMState.INSERT_REVERSE_6
    );
    private final List<HMMState> startStopStates = List.of(
            HMMState.START,
            HMMState.START_REVERSE,
            HMMState.END,
            HMMState.END_REVERSE
    );

    @Test
    public void previousState() {
        // For M states
        assertEquals(HMMState.MATCH_6, HMMState.previousState(HMMState.MATCH_1));
        assertEquals(HMMState.MATCH_1, HMMState.previousState(HMMState.MATCH_2));
        assertEquals(HMMState.MATCH_2, HMMState.previousState(HMMState.MATCH_3));
        assertEquals(HMMState.MATCH_3, HMMState.previousState(HMMState.MATCH_4));
        assertEquals(HMMState.MATCH_4, HMMState.previousState(HMMState.MATCH_5));
        assertEquals(HMMState.MATCH_5, HMMState.previousState(HMMState.MATCH_6));

        // For M' states
        assertEquals(HMMState.MATCH_REVERSE_6, HMMState.previousState(HMMState.MATCH_REVERSE_1));
        assertEquals(HMMState.MATCH_REVERSE_1, HMMState.previousState(HMMState.MATCH_REVERSE_2));
        assertEquals(HMMState.MATCH_REVERSE_2, HMMState.previousState(HMMState.MATCH_REVERSE_3));
        assertEquals(HMMState.MATCH_REVERSE_3, HMMState.previousState(HMMState.MATCH_REVERSE_4));
        assertEquals(HMMState.MATCH_REVERSE_4, HMMState.previousState(HMMState.MATCH_REVERSE_5));
        assertEquals(HMMState.MATCH_REVERSE_5, HMMState.previousState(HMMState.MATCH_REVERSE_6));

        // For I states
        for (HMMState state : insertStates) {
            assertEquals(HMMState.NO_STATE, HMMState.previousState(state));
        }

        // For I' states
        for (HMMState state : insertReverseStates) {
            assertEquals(HMMState.NO_STATE, HMMState.previousState(state));
        }

        // For Start/Stop states
        for (HMMState state : startStopStates) {
            assertEquals(HMMState.NO_STATE, HMMState.previousState(state));
        }

        // For Noncoding state
        assertEquals(HMMState.NO_STATE, HMMState.previousState(HMMState.NON_CODING));

        // For No state
        assertEquals(HMMState.NO_STATE, HMMState.previousState(HMMState.NO_STATE));
    }

    @Test
    public void insertStateForMatching() {
        // For M states
        assertEquals(HMMState.INSERT_6, HMMState.insertStateForMatching(HMMState.MATCH_1));
        assertEquals(HMMState.INSERT_1, HMMState.insertStateForMatching(HMMState.MATCH_2));
        assertEquals(HMMState.INSERT_2, HMMState.insertStateForMatching(HMMState.MATCH_3));
        assertEquals(HMMState.INSERT_3, HMMState.insertStateForMatching(HMMState.MATCH_4));
        assertEquals(HMMState.INSERT_4, HMMState.insertStateForMatching(HMMState.MATCH_5));
        assertEquals(HMMState.INSERT_5, HMMState.insertStateForMatching(HMMState.MATCH_6));

        // For M' states
        assertEquals(HMMState.INSERT_REVERSE_6, HMMState.insertStateForMatching(HMMState.MATCH_REVERSE_1));
        assertEquals(HMMState.INSERT_REVERSE_1, HMMState.insertStateForMatching(HMMState.MATCH_REVERSE_2));
        assertEquals(HMMState.INSERT_REVERSE_2, HMMState.insertStateForMatching(HMMState.MATCH_REVERSE_3));
        assertEquals(HMMState.INSERT_REVERSE_3, HMMState.insertStateForMatching(HMMState.MATCH_REVERSE_4));
        assertEquals(HMMState.INSERT_REVERSE_4, HMMState.insertStateForMatching(HMMState.MATCH_REVERSE_5));
        assertEquals(HMMState.INSERT_REVERSE_5, HMMState.insertStateForMatching(HMMState.MATCH_REVERSE_6));

        // For I states
        for (HMMState state : insertStates) {
            assertEquals(HMMState.NO_STATE, HMMState.insertStateForMatching(state));
        }

        // For I' states
        for (HMMState state : insertReverseStates) {
            assertEquals(HMMState.NO_STATE, HMMState.insertStateForMatching(state));
        }

        // For Start/Stop states
        for (HMMState state : startStopStates) {
            assertEquals(HMMState.NO_STATE, HMMState.insertStateForMatching(state));
        }

        // For Noncoding state
        assertEquals(HMMState.NO_STATE, HMMState.insertStateForMatching(HMMState.NON_CODING));

        // For No state
        assertEquals(HMMState.NO_STATE, HMMState.insertStateForMatching(HMMState.NO_STATE));
    }

    @Test
    public void matchingStateForInsert() {
        // For M states
        assertEquals(HMMState.MATCH_1, HMMState.matchingStateForInsert(HMMState.INSERT_1));
        assertEquals(HMMState.MATCH_2, HMMState.matchingStateForInsert(HMMState.INSERT_2));
        assertEquals(HMMState.MATCH_3, HMMState.matchingStateForInsert(HMMState.INSERT_3));
        assertEquals(HMMState.MATCH_4, HMMState.matchingStateForInsert(HMMState.INSERT_4));
        assertEquals(HMMState.MATCH_5, HMMState.matchingStateForInsert(HMMState.INSERT_5));
        assertEquals(HMMState.MATCH_6, HMMState.matchingStateForInsert(HMMState.INSERT_6));

        // For M' states
        assertEquals(HMMState.MATCH_REVERSE_1, HMMState.matchingStateForInsert(HMMState.INSERT_REVERSE_1));
        assertEquals(HMMState.MATCH_REVERSE_2, HMMState.matchingStateForInsert(HMMState.INSERT_REVERSE_2));
        assertEquals(HMMState.MATCH_REVERSE_3, HMMState.matchingStateForInsert(HMMState.INSERT_REVERSE_3));
        assertEquals(HMMState.MATCH_REVERSE_4, HMMState.matchingStateForInsert(HMMState.INSERT_REVERSE_4));
        assertEquals(HMMState.MATCH_REVERSE_5, HMMState.matchingStateForInsert(HMMState.INSERT_REVERSE_5));
        assertEquals(HMMState.MATCH_REVERSE_6, HMMState.matchingStateForInsert(HMMState.INSERT_REVERSE_6));

        // For I states
        for (HMMState state : matchingStates) {
            assertEquals(HMMState.NO_STATE, HMMState.matchingStateForInsert(state));
        }

        // For I' states
        for (HMMState state : matchingReverseStates) {
            assertEquals(HMMState.NO_STATE, HMMState.matchingStateForInsert(state));
        }

        // For Start/Stop states
        for (HMMState state : startStopStates) {
            assertEquals(HMMState.NO_STATE, HMMState.matchingStateForInsert(state));
        }

        // For Noncoding state
        assertEquals(HMMState.NO_STATE, HMMState.matchingStateForInsert(HMMState.NON_CODING));

        // For No state
        assertEquals(HMMState.NO_STATE, HMMState.matchingStateForInsert(HMMState.NO_STATE));
    }

    @Test
    public void fromString() {
        assertEquals(HMMState.START, HMMState.fromString("S"));
        assertEquals(HMMState.END, HMMState.fromString("E"));
        assertEquals(HMMState.NON_CODING, HMMState.fromString("R"));
        assertEquals(HMMState.START_REVERSE, HMMState.fromString("S_1"));
        assertEquals(HMMState.END_REVERSE, HMMState.fromString("E_1"));
        assertEquals(HMMState.MATCH_1, HMMState.fromString("M1"));
        assertEquals(HMMState.MATCH_2, HMMState.fromString("M2"));
        assertEquals(HMMState.MATCH_3, HMMState.fromString("M3"));
        assertEquals(HMMState.MATCH_4, HMMState.fromString("M4"));
        assertEquals(HMMState.MATCH_5, HMMState.fromString("M5"));
        assertEquals(HMMState.MATCH_6, HMMState.fromString("M6"));
        assertEquals(HMMState.MATCH_REVERSE_1, HMMState.fromString("M1_1"));
        assertEquals(HMMState.MATCH_REVERSE_2, HMMState.fromString("M2_1"));
        assertEquals(HMMState.MATCH_REVERSE_3, HMMState.fromString("M3_1"));
        assertEquals(HMMState.MATCH_REVERSE_4, HMMState.fromString("M4_1"));
        assertEquals(HMMState.MATCH_REVERSE_5, HMMState.fromString("M5_1"));
        assertEquals(HMMState.MATCH_REVERSE_6, HMMState.fromString("M6_1"));
        assertEquals(HMMState.INSERT_1, HMMState.fromString("I1"));
        assertEquals(HMMState.INSERT_2, HMMState.fromString("I2"));
        assertEquals(HMMState.INSERT_3, HMMState.fromString("I3"));
        assertEquals(HMMState.INSERT_4, HMMState.fromString("I4"));
        assertEquals(HMMState.INSERT_5, HMMState.fromString("I5"));
        assertEquals(HMMState.INSERT_6, HMMState.fromString("I6"));
        assertEquals(HMMState.INSERT_REVERSE_1, HMMState.fromString("I1_1"));
        assertEquals(HMMState.INSERT_REVERSE_2, HMMState.fromString("I2_1"));
        assertEquals(HMMState.INSERT_REVERSE_3, HMMState.fromString("I3_1"));
        assertEquals(HMMState.INSERT_REVERSE_4, HMMState.fromString("I4_1"));
        assertEquals(HMMState.INSERT_REVERSE_5, HMMState.fromString("I5_1"));
        assertEquals(HMMState.INSERT_REVERSE_6, HMMState.fromString("I6_1"));

        assertEquals(HMMState.NO_STATE, HMMState.fromString(""));
        assertEquals(HMMState.NO_STATE, HMMState.fromString("M7"));
        assertEquals(HMMState.NO_STATE, HMMState.fromString("I4_2"));
    }

    @Test
    public void isMatchState() {
        for (HMMState state : matchingStates) {
            assertTrue(HMMState.isMatchState(state));
        }

        for (HMMState state : matchingReverseStates) {
            assertTrue(HMMState.isMatchState(state));
        }

        for (HMMState state : insertStates) {
            assertFalse(HMMState.isMatchState(state));
        }

        for (HMMState state : insertReverseStates) {
            assertFalse(HMMState.isMatchState(state));
        }

        for (HMMState state : startStopStates) {
            assertFalse(HMMState.isMatchState(state));
        }

        assertFalse(HMMState.isMatchState(HMMState.NON_CODING));
        assertFalse(HMMState.isMatchState(HMMState.NO_STATE));
    }

    @Test
    public void isStartState() {
        for (HMMState state : matchingStates) {
            assertFalse(HMMState.isStartState(state));
        }

        for (HMMState state : matchingReverseStates) {
            assertFalse(HMMState.isStartState(state));
        }

        for (HMMState state : insertStates) {
            assertFalse(HMMState.isStartState(state));
        }

        for (HMMState state : insertReverseStates) {
            assertFalse(HMMState.isStartState(state));
        }


        assertTrue(HMMState.isStartState(HMMState.START));
        assertTrue(HMMState.isStartState(HMMState.START_REVERSE));
        assertFalse(HMMState.isStartState(HMMState.END));
        assertFalse(HMMState.isStartState(HMMState.END_REVERSE));

        assertFalse(HMMState.isStartState(HMMState.NON_CODING));
        assertFalse(HMMState.isStartState(HMMState.NO_STATE));
    }

    @Test
    public void isForwardMatchState() {
        for (HMMState state : matchingStates) {
            assertTrue(HMMState.isForwardMatchState(state));
        }

        for (HMMState state : matchingReverseStates) {
            assertFalse(HMMState.isForwardMatchState(state));
        }

        for (HMMState state : insertStates) {
            assertFalse(HMMState.isForwardMatchState(state));
        }

        for (HMMState state : insertReverseStates) {
            assertFalse(HMMState.isForwardMatchState(state));
        }

        for (HMMState state : startStopStates) {
            assertFalse(HMMState.isForwardMatchState(state));
        }

        assertFalse(HMMState.isForwardMatchState(HMMState.NON_CODING));
        assertFalse(HMMState.isForwardMatchState(HMMState.NO_STATE));
    }

    @Test
    public void isReverseMatchState() {
        for (HMMState state : matchingStates) {
            assertFalse(HMMState.isReverseMatchState(state));
        }

        for (HMMState state : matchingReverseStates) {
            assertTrue(HMMState.isReverseMatchState(state));
        }

        for (HMMState state : insertStates) {
            assertFalse(HMMState.isReverseMatchState(state));
        }

        for (HMMState state : insertReverseStates) {
            assertFalse(HMMState.isReverseMatchState(state));
        }

        for (HMMState state : startStopStates) {
            assertFalse(HMMState.isReverseMatchState(state));
        }

        assertFalse(HMMState.isReverseMatchState(HMMState.NON_CODING));
        assertFalse(HMMState.isReverseMatchState(HMMState.NO_STATE));
    }

    @Test
    public void nextState() {
        // For M states
        assertEquals(HMMState.MATCH_2, HMMState.nextState(HMMState.MATCH_1));
        assertEquals(HMMState.MATCH_3, HMMState.nextState(HMMState.MATCH_2));
        assertEquals(HMMState.MATCH_4, HMMState.nextState(HMMState.MATCH_3));
        assertEquals(HMMState.MATCH_5, HMMState.nextState(HMMState.MATCH_4));
        assertEquals(HMMState.MATCH_6, HMMState.nextState(HMMState.MATCH_5));
        assertEquals(HMMState.MATCH_1, HMMState.nextState(HMMState.MATCH_6));

        // For M' states
        assertEquals(HMMState.MATCH_REVERSE_2, HMMState.nextState(HMMState.MATCH_REVERSE_1));
        assertEquals(HMMState.MATCH_REVERSE_3, HMMState.nextState(HMMState.MATCH_REVERSE_2));
        assertEquals(HMMState.MATCH_REVERSE_4, HMMState.nextState(HMMState.MATCH_REVERSE_3));
        assertEquals(HMMState.MATCH_REVERSE_5, HMMState.nextState(HMMState.MATCH_REVERSE_4));
        assertEquals(HMMState.MATCH_REVERSE_6, HMMState.nextState(HMMState.MATCH_REVERSE_5));
        assertEquals(HMMState.MATCH_REVERSE_1, HMMState.nextState(HMMState.MATCH_REVERSE_6));

        // For I states
        for (HMMState state : insertStates) {
            assertEquals(HMMState.NO_STATE, HMMState.nextState(state));
        }

        // For I' states
        for (HMMState state : insertReverseStates) {
            assertEquals(HMMState.NO_STATE, HMMState.nextState(state));
        }

        // For Start/Stop states
        for (HMMState state : startStopStates) {
            assertEquals(HMMState.NO_STATE, HMMState.nextState(state));
        }

        // For Noncoding state
        assertEquals(HMMState.NO_STATE, HMMState.nextState(HMMState.NON_CODING));

        // For No state
        assertEquals(HMMState.NO_STATE, HMMState.nextState(HMMState.NO_STATE));
    }
}
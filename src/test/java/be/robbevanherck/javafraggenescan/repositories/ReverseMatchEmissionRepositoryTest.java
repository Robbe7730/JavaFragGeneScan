package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReverseMatchEmissionRepositoryTest {

    @Test
    public void matchStateFromInt() {
        ReverseMatchEmissionRepository.createInstance();
        ReverseMatchEmissionRepository reverseMatchEmissionRepository = ReverseMatchEmissionRepository.getInstance();
        assertEquals(HMMState.MATCH_REVERSE_1, reverseMatchEmissionRepository.matchStateFromInt(1));
        assertEquals(HMMState.MATCH_REVERSE_2, reverseMatchEmissionRepository.matchStateFromInt(2));
        assertEquals(HMMState.MATCH_REVERSE_3, reverseMatchEmissionRepository.matchStateFromInt(3));
        assertEquals(HMMState.MATCH_REVERSE_4, reverseMatchEmissionRepository.matchStateFromInt(4));
        assertEquals(HMMState.MATCH_REVERSE_5, reverseMatchEmissionRepository.matchStateFromInt(5));
        assertEquals(HMMState.MATCH_REVERSE_6, reverseMatchEmissionRepository.matchStateFromInt(6));

        assertEquals(HMMState.NO_STATE, reverseMatchEmissionRepository.matchStateFromInt(7));
        assertEquals(HMMState.NO_STATE, reverseMatchEmissionRepository.matchStateFromInt(0));
        assertEquals(HMMState.NO_STATE, reverseMatchEmissionRepository.matchStateFromInt(-1));
    }
}
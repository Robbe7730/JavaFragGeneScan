package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForwardMatchEmissionRepositoryTest {

    @Test
    public void matchStateFromInt() {
        ForwardMatchEmissionRepository.createInstance();
        ForwardMatchEmissionRepository forwardMatchEmissionRepository = ForwardMatchEmissionRepository.getInstance();
        assertEquals(HMMState.MATCH_1, forwardMatchEmissionRepository.matchStateFromInt(1));
        assertEquals(HMMState.MATCH_2, forwardMatchEmissionRepository.matchStateFromInt(2));
        assertEquals(HMMState.MATCH_3, forwardMatchEmissionRepository.matchStateFromInt(3));
        assertEquals(HMMState.MATCH_4, forwardMatchEmissionRepository.matchStateFromInt(4));
        assertEquals(HMMState.MATCH_5, forwardMatchEmissionRepository.matchStateFromInt(5));
        assertEquals(HMMState.MATCH_6, forwardMatchEmissionRepository.matchStateFromInt(6));

        assertEquals(HMMState.NO_STATE, forwardMatchEmissionRepository.matchStateFromInt(7));
        assertEquals(HMMState.NO_STATE, forwardMatchEmissionRepository.matchStateFromInt(0));
        assertEquals(HMMState.NO_STATE, forwardMatchEmissionRepository.matchStateFromInt(-1));
    }
}
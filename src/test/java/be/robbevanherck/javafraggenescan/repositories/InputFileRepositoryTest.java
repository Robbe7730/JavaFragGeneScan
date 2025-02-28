package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

public class InputFileRepositoryTest {
    InputFileRepository instance;

    @Before
    public void createInstance() {
        // Using a dummy input file, we check if the values are read and returned correctly
        InputFileRepository.createInstance(new File("train/dummy_input"));
        instance = InputFileRepository.getInstance();
    }

    @Test
    public void getOuterTransitions() {
        Map<HMMOuterTransition, Double> outerTransitions = instance.getOuterTransitions();
        assertEquals(0.1111, outerTransitions.get(HMMOuterTransition.GENE_GENE), 0);
        assertEquals(0.2222, outerTransitions.get(HMMOuterTransition.GENE_END), 0);
        assertEquals(0.3333, outerTransitions.get(HMMOuterTransition.END_NONCODING), 0);
        assertEquals(0.4444, outerTransitions.get(HMMOuterTransition.END_START_SAME), 0);
        assertEquals(0.5555, outerTransitions.get(HMMOuterTransition.END_START_REVERSE), 0);
        assertEquals(0.6666, outerTransitions.get(HMMOuterTransition.NONCODING_START), 0);
        assertEquals(0.7777, outerTransitions.get(HMMOuterTransition.NONCODING_NONCODING), 0);
    }

    @Test
    public void getInnerTransitions() {
        Map<HMMInnerTransition, Double> innerTransitions = instance.getInnerTransitions();
        assertEquals(0.8888, innerTransitions.get(HMMInnerTransition.MATCH_MATCH), 0);
        assertEquals(0.9999, innerTransitions.get(HMMInnerTransition.MATCH_INSERT), 0);
        assertEquals(0.1112, innerTransitions.get(HMMInnerTransition.MATCH_DELETE), 0);
        assertEquals(0.2223, innerTransitions.get(HMMInnerTransition.INSERT_INSERT), 0);
        assertEquals(0.3334, innerTransitions.get(HMMInnerTransition.INSERT_MATCH), 0);
        assertEquals(0.4445, innerTransitions.get(HMMInnerTransition.DELETE_DELETE), 0);
        assertEquals(0.5556, innerTransitions.get(HMMInnerTransition.DELETE_MATCH), 0);
    }

    @Test
    public void getMatchInsertEmissions() {
        Map<Pair<AminoAcid>, Double> matchInsertEmissions = instance.getMatchInsertEmissions();
        assertEquals(0.6667, matchInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.A)), 0);
        assertEquals(0.7778, matchInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.C)), 0);
        assertEquals(0.8889, matchInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.G)), 0);
        assertEquals(0.9990, matchInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.T)), 0);
        assertEquals(0.1122, matchInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.A)), 0);
        assertEquals(0.2233, matchInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.C)), 0);
        assertEquals(0.3344, matchInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.G)), 0);
        assertEquals(0.4455, matchInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.T)), 0);
        assertEquals(0.5566, matchInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.A)), 0);
        assertEquals(0.6677, matchInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.C)), 0);
        assertEquals(0.7788, matchInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.G)), 0);
        assertEquals(0.8899, matchInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.T)), 0);
        assertEquals(0.9900, matchInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.A)), 0);
        assertEquals(0.1222, matchInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.C)), 0);
        assertEquals(0.2111, matchInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.G)), 0);
        assertEquals(0.3222, matchInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.T)), 0);
    }

    @Test
    public void getInsertInsertEmissions() {
        Map<Pair<AminoAcid>, Double> insertInsertEmissions = instance.getInsertInsertEmissions();
        assertEquals(0.4333, insertInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.A)), 0);
        assertEquals(0.5444, insertInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.C)), 0);
        assertEquals(0.6555, insertInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.G)), 0);
        assertEquals(0.7666, insertInsertEmissions.get(new Pair<>(AminoAcid.A, AminoAcid.T)), 0);
        assertEquals(0.8777, insertInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.A)), 0);
        assertEquals(0.9888, insertInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.C)), 0);
        assertEquals(0.1234, insertInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.G)), 0);
        assertEquals(0.2345, insertInsertEmissions.get(new Pair<>(AminoAcid.C, AminoAcid.T)), 0);
        assertEquals(0.3456, insertInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.A)), 0);
        assertEquals(0.4567, insertInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.C)), 0);
        assertEquals(0.5678, insertInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.G)), 0);
        assertEquals(0.6789, insertInsertEmissions.get(new Pair<>(AminoAcid.G, AminoAcid.T)), 0);
        assertEquals(0.7890, insertInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.A)), 0);
        assertEquals(0.8901, insertInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.C)), 0);
        assertEquals(0.9012, insertInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.G)), 0);
        assertEquals(0.0123, insertInsertEmissions.get(new Pair<>(AminoAcid.T, AminoAcid.T)), 0);
    }

    @Test
    public void getInitialProbabilities() {
        Map<HMMState, PathProbability> initialProbabilities = instance.getInitialProbabilities();
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.9876), initialProbabilities.get(HMMState.START));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.8765), initialProbabilities.get(HMMState.END));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.7654), initialProbabilities.get(HMMState.NON_CODING));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.6543), initialProbabilities.get(HMMState.START_REVERSE));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.5432), initialProbabilities.get(HMMState.END_REVERSE));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.4321), initialProbabilities.get(HMMState.MATCH_1));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.3210), initialProbabilities.get(HMMState.MATCH_2));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.2109), initialProbabilities.get(HMMState.MATCH_3));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1098), initialProbabilities.get(HMMState.MATCH_4));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.0987), initialProbabilities.get(HMMState.MATCH_5));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1113), initialProbabilities.get(HMMState.MATCH_6));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1114), initialProbabilities.get(HMMState.MATCH_REVERSE_1));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1115), initialProbabilities.get(HMMState.MATCH_REVERSE_2));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1116), initialProbabilities.get(HMMState.MATCH_REVERSE_3));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1117), initialProbabilities.get(HMMState.MATCH_REVERSE_4));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1118), initialProbabilities.get(HMMState.MATCH_REVERSE_5));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1119), initialProbabilities.get(HMMState.MATCH_REVERSE_6));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1120), initialProbabilities.get(HMMState.INSERT_1));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1121), initialProbabilities.get(HMMState.INSERT_2));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1123), initialProbabilities.get(HMMState.INSERT_3));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1124), initialProbabilities.get(HMMState.INSERT_4));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1125), initialProbabilities.get(HMMState.INSERT_5));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1126), initialProbabilities.get(HMMState.INSERT_6));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1127), initialProbabilities.get(HMMState.INSERT_REVERSE_1));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1128), initialProbabilities.get(HMMState.INSERT_REVERSE_2));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1129), initialProbabilities.get(HMMState.INSERT_REVERSE_3));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1130), initialProbabilities.get(HMMState.INSERT_REVERSE_4));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1131), initialProbabilities.get(HMMState.INSERT_REVERSE_5));
        assertEquals(new PathProbability(HMMState.NO_STATE, 0.1132), initialProbabilities.get(HMMState.INSERT_REVERSE_6));
    }
}
package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.InvalidTrainingFileException;
import be.robbevanherck.javafraggenescan.entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Repository for the data in the input HMM file
 */
public class InputFileRepository {
    private InputFileRepository() {}

    private static Map<HMMInnerTransition, Double> innerTransitions;
    private static Map<HMMOuterTransition, Double> outerTransitions;
    private static Map<Pair<AminoAcid>, Double> matchInsertEmissions;
    private static Map<Pair<AminoAcid>, Double> insertInsertEmissions;
    private static Map<HMMState, Double> initialProbabilities;

    /**
     * Setup the repository by reading the file
     * @param file The file, given as a command-line argument
     */
    public static void setup(File file) {
        innerTransitions = new EnumMap<>(HMMInnerTransition.class);
        outerTransitions = new EnumMap<>(HMMOuterTransition.class);
        matchInsertEmissions = new HashMap<>();
        insertInsertEmissions = new HashMap<>();
        initialProbabilities = new EnumMap<>(HMMState.class);

        try(Scanner s = new Scanner(file)) {
            while(s.hasNext()) {
                /* Transitions */
                s.next(); // Skip the header

                /* Outer Transitions */
                for (int i = 0; i < HMMOuterTransition.values().length - 1; i++) {
                    String transitionString = s.next();
                    double transitionProbability = s.nextDouble();

                    HMMOuterTransition transition = HMMOuterTransition.fromString(transitionString);

                    outerTransitions.put(transition, transitionProbability);
                }

                /* Inner Transitions */
                for (int i = 0; i < HMMInnerTransition.values().length - 1; i++) {
                    String transitionString = s.next();
                    double transitionProbability = s.nextDouble();

                    HMMInnerTransition transition = HMMInnerTransition.fromString(transitionString);

                    innerTransitions.put(transition, transitionProbability);
                }

                /* Match-Insert Emissions */
                s.next(); // Skip the header
                for (int i = 0; i < 16; i++) {
                    String firstAminoAcidString = s.next();
                    String secondAminoAcidString = s.next();
                    double miEmissionProbability = s.nextDouble();

                    AminoAcid firstAminoAcid = AminoAcid.fromString(firstAminoAcidString);
                    AminoAcid secondAminoAcid = AminoAcid.fromString(secondAminoAcidString);
                    Pair<AminoAcid> pair = new Pair<>(firstAminoAcid, secondAminoAcid);

                    matchInsertEmissions.put(pair, miEmissionProbability);
                }

                /* Insert-Insert Emissions */
                s.next(); // Skip the header
                for (int i = 0; i < 16; i++) {
                    String firstAminoAcidString = s.next();
                    String secondAminoAcidString = s.next();
                    double iiEmissionProbability = s.nextDouble();

                    AminoAcid firstAminoAcid = AminoAcid.fromString(firstAminoAcidString);
                    AminoAcid secondAminoAcid = AminoAcid.fromString(secondAminoAcidString);
                    Pair<AminoAcid> pair = new Pair<>(firstAminoAcid, secondAminoAcid);

                    insertInsertEmissions.put(pair, iiEmissionProbability);
                }

                /* Initial State Probabilities */
                s.next(); // Skip the header
                for (int i = 0; i < HMMState.values().length - 1; i++) {
                    String stateString = s.next();
                    double stateProbability = s.nextDouble();

                    HMMState state = HMMState.fromString(stateString);

                    initialProbabilities.put(state, stateProbability);
                }
            }
        } catch (FileNotFoundException fnef) {
            throw new InvalidTrainingFileException("No such file: " + file.getAbsolutePath(), fnef);
        }
    }

    public static Map<HMMOuterTransition, Double> getOuterTransitions() {
        return outerTransitions;
    }

    public static Map<HMMInnerTransition, Double> getInnerTransitions() {
        return innerTransitions;
    }

    public static Map<Pair<AminoAcid>, Double> getMatchInsertEmissions() {
        return matchInsertEmissions;
    }

    public static Map<Pair<AminoAcid>, Double> getInsertInsertEmissions() {
        return insertInsertEmissions;
    }

    public static Map<HMMState, Double> getInitialProbabilities() {
        return initialProbabilities;
    }
}

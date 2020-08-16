package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Repository for the pwm file
 */
public class GaussianArgumentsRepository extends CGDependentRepository<Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, Double>>> {
    private static CGDependentRepository<Map<HMMState, Map<GaussianArgument, Double>>> instance;

    /**
     * Create a new PWMRepository
     */
    public GaussianArgumentsRepository() {
        super("train/pwm");
    }

    /**
     * Create a new instance for this Repository
     */
    public static void createInstance() {
        instance = new GaussianArgumentsRepository();
    }

    public static CGDependentRepository<Map<HMMState, Map<GaussianArgument, Double>>> getInstance() {
        return instance;
    }

    @Override
    protected Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, Double>> readOneBlock(Scanner s) {
        Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, Double>> ret = new EnumMap<>(HMMState.class);

        /* Start */
        Map<GaussianArgumentsRepository.GaussianArgument, Double> newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, s.nextDouble());
        }
        ret.put(HMMState.START, newRow);

        /* End */
        newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, s.nextDouble());
        }
        ret.put(HMMState.END, newRow);

        /* Start Reverse */
        newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, s.nextDouble());
        }
        ret.put(HMMState.START_REVERSE, newRow);

        /* End Reverse */
        newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, s.nextDouble());
        }
        ret.put(HMMState.END_REVERSE, newRow);

        return ret;
    }

    @Override
    protected int readIndex(Scanner s) {
        return s.nextInt();
    }

    /**
     * Arguments for the Gaussian curves used to predict start/stop codons
     */
    public enum GaussianArgument {
        SIGMA,
        MU,
        ALPHA,
        SIGMA_R,
        MU_R,
        ALPHA_R
    }
}

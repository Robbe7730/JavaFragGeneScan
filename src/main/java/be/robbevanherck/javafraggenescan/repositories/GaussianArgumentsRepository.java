package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Repository for the pwm file
 */
public class GaussianArgumentsRepository extends CGDependentRepository<Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, BigDecimal>>> {
    private static GaussianArgumentsRepository instance;

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

    public static GaussianArgumentsRepository getInstance() {
        return instance;
    }

    @Override
    protected Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, BigDecimal>> readOneBlock(Scanner s) {
        Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, BigDecimal>> ret = new EnumMap<>(HMMState.class);

        /* Start */
        Map<GaussianArgumentsRepository.GaussianArgument, BigDecimal> newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, BigDecimal.valueOf(s.nextDouble()));
        }
        ret.put(HMMState.START, newRow);

        /* End */
        newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, BigDecimal.valueOf(s.nextDouble()));
        }
        ret.put(HMMState.END, newRow);

        /* Start Reverse */
        newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, BigDecimal.valueOf(s.nextDouble()));
        }
        ret.put(HMMState.START_REVERSE, newRow);

        /* End Reverse */
        newRow = new EnumMap<>(GaussianArgumentsRepository.GaussianArgument.class);
        for (GaussianArgument argument : GaussianArgument.values()) {
            newRow.put(argument, BigDecimal.valueOf(s.nextDouble()));
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

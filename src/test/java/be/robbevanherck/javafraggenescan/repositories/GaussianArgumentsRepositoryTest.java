package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class GaussianArgumentsRepositoryTest {

    @Test
    public void getValues() {
        GaussianArgumentsRepository.createInstance();
        GaussianArgumentsRepository instance = (GaussianArgumentsRepository) GaussianArgumentsRepository.getInstance();

        // Since we know the values of the gaussian arguments, we can check if it reads train/pwm correctly
        Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, Double>> values = instance.getValues(50);

        assertEquals(9.4438, values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.SIGMA), 0);
        assertEquals(232.8660, values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.MU), 0);
        assertEquals(0.0834, values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.ALPHA), 0);
        assertEquals(8.8747, values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R), 0);
        assertEquals(252.8618, values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.MU_R), 0);
        assertEquals(0.0884, values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R), 0);

        assertEquals(6.4592, values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.SIGMA), 0);
        assertEquals(238.8829, values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.MU), 0);
        assertEquals(0.1222, values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.ALPHA), 0);
        assertEquals(10.9225, values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R), 0);
        assertEquals(261.2409, values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.MU_R), 0);
        assertEquals(0.0728, values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R), 0);

        assertEquals(6.8477, values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA), 0);
        assertEquals(238.7324, values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU), 0);
        assertEquals(0.1174, values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA), 0);
        assertEquals(11.2112, values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R), 0);
        assertEquals(261.2417, values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU_R), 0);
        assertEquals(0.0714, values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R), 0);

        assertEquals(9.0684, values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA), 0);
        assertEquals(231.9033, values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU), 0);
        assertEquals(0.0862, values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA), 0);
        assertEquals(9.3179, values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R), 0);
        assertEquals(253.8141, values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU_R), 0);
        assertEquals(0.0845, values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R), 0);
    }
}
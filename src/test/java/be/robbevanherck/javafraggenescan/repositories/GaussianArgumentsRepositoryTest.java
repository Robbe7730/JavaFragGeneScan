package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class GaussianArgumentsRepositoryTest {

    @Test
    public void getValues() {
        GaussianArgumentsRepository.createInstance();
        GaussianArgumentsRepository instance = GaussianArgumentsRepository.getInstance();

        // Since we know the values of the gaussian arguments, we can check if it reads train/pwm correctly
        Map<HMMState, Map<GaussianArgumentsRepository.GaussianArgument, BigDecimal>> values = instance.getValues(50);

        assertEquals(BigDecimal.valueOf(9.4438), values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.SIGMA));
        assertEquals(BigDecimal.valueOf(232.8660), values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.MU));
        assertEquals(BigDecimal.valueOf(0.0834), values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.ALPHA));
        assertEquals(BigDecimal.valueOf(8.8747), values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R));
        assertEquals(BigDecimal.valueOf(252.8618), values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.MU_R));
        assertEquals(BigDecimal.valueOf(0.0884), values.get(HMMState.START).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R));

        assertEquals(BigDecimal.valueOf(6.4592), values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.SIGMA));
        assertEquals(BigDecimal.valueOf(238.8829), values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.MU));
        assertEquals(BigDecimal.valueOf(0.1222), values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.ALPHA));
        assertEquals(BigDecimal.valueOf(10.9225), values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R));
        assertEquals(BigDecimal.valueOf(261.2409), values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.MU_R));
        assertEquals(BigDecimal.valueOf(0.0728), values.get(HMMState.END).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R));

        assertEquals(BigDecimal.valueOf(6.8477), values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA));
        assertEquals(BigDecimal.valueOf(238.7324), values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU));
        assertEquals(BigDecimal.valueOf(0.1174), values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA));
        assertEquals(BigDecimal.valueOf(11.2112), values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R));
        assertEquals(BigDecimal.valueOf(261.2417), values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU_R));
        assertEquals(BigDecimal.valueOf(0.0714), values.get(HMMState.START_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R));

        assertEquals(BigDecimal.valueOf(9.0684), values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA));
        assertEquals(BigDecimal.valueOf(231.9033), values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU));
        assertEquals(BigDecimal.valueOf(0.0862), values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA));
        assertEquals(BigDecimal.valueOf(9.3179), values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.SIGMA_R));
        assertEquals(BigDecimal.valueOf(253.8141), values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.MU_R));
        assertEquals(BigDecimal.valueOf(0.0845), values.get(HMMState.END_REVERSE).get(GaussianArgumentsRepository.GaussianArgument.ALPHA_R));
    }
}
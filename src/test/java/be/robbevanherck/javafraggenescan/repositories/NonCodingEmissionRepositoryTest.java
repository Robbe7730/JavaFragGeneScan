package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Pair;
import org.junit.Test;

import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static be.robbevanherck.javafraggenescan.TestUtil.aminoAcids;
import static org.junit.Assert.*;

public class NonCodingEmissionRepositoryTest {

    @Test
    public void read() {
        // Return and file handling are already tested in CGDependentRepositoryTest, only the reading
        // needs to be tested here
        NonCodingEmissionRepository nonCodingEmissionRepository = new NonCodingEmissionRepository();
        Scanner blockScanner = new Scanner(
                "0.0000\t0.0001\t0.0002\t0.0003\n" +
                "0.0004\t0.0005\t0.0006\t0.0007\n" +
                "0.0008\t0.0009\t0.0010\t0.0011\n" +
                "0.0012\t0.0013\t0.0014\t0.0015\n");
        Map<Pair<AminoAcid>, BigDecimal> blockResult = nonCodingEmissionRepository.readOneBlock(blockScanner);

        BigDecimal i = BigDecimal.ZERO;

        for (AminoAcid firstAcid : aminoAcids) {
            for (AminoAcid secondAcid : aminoAcids) {
                assertEquals(i.doubleValue(), blockResult.get(new Pair<>(firstAcid, secondAcid)).doubleValue(), 0);
                i = i.add(BigDecimal.valueOf(0.0001));
            }
        }
    }
}
package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.dummies.DummyPrintStream;
import org.junit.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class ViterbiResultTest {
    private final ViterbiResult testResult = new ViterbiResult(
            List.of(AminoAcid.A, AminoAcid.T, AminoAcid.G),
            0,
            1,
            DNAStrand.FORWARD,
            "TEST"
    );

    @Test
    public void getValues() {
        assertEquals("ATG", testResult.getDNA());
        assertEquals("M", testResult.getProteins());
        assertEquals("_0_1_+", testResult.getHeaderSuffix());
    }

    @Test
    public void write() {
        DummyPrintStream dummyFastaPrintStream = new DummyPrintStream();
        testResult.writeFasta(dummyFastaPrintStream);
        assertEquals(">TEST_0_1_+\nATG\n", dummyFastaPrintStream.getResult());

        DummyPrintStream dummyProteinPrintStream = new DummyPrintStream();
        testResult.writeProteins(dummyProteinPrintStream);
        assertEquals(">TEST_0_1_+\nM\n", dummyProteinPrintStream.getResult());
    }
}
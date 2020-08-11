package be.robbevanherck.javafraggenescan.entities;

import org.junit.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class ViterbiResultTest {
    private static class DummyPrintStream extends PrintStream {
        String result;
        public DummyPrintStream() {
            super(OutputStream.nullOutputStream());
            this.result = "";
        }

        @Override
        public PrintStream append(CharSequence csq) {
            result += csq.toString();
            return this;
        }

        @Override
        public PrintStream append(char c) {
            result += c;
            return this;
        }

        public String getResult() {
            return result;
        }
    }

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
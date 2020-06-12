package be.robbevanherck.javafraggenescan.util;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * Reads FASTA files from stdin
 */
public class FASTAInputReader {
    /**
     * Private constructor to hide the implicit public one
     */
    private FASTAInputReader() {}

    /**
     * Read the FASTA file from stdin
     * @return A LinkedHashMap containing (String name, DNASequence sequence)
     */
    public static Map<String, DNASequence> readFastaStdin() {
        InputStream inputStream = System.in;
        try {
            return FastaReaderHelper.readFastaDNASequence(inputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyMap();
    }
}

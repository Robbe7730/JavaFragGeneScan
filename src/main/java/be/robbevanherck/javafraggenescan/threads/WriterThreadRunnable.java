package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.entities.ViterbiResult;
import be.robbevanherck.javafraggenescan.exceptions.OutputException;
import org.sonatype.inject.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Runnable for the writer thread
 */
public class WriterThreadRunnable implements Runnable {

    @Nullable
    private final File outputDNAFASTA;

    /**
     * Create a new WriterThreadRunnable
     * @param outputDNAFASTA The file to write the DNA strings to, null to not write it
     */
    public WriterThreadRunnable(File outputDNAFASTA) {
        this.outputDNAFASTA = outputDNAFASTA;
    }

    @Override
    public void run() {
        PrintStream fastaOutputStream = null;
        if (outputDNAFASTA != null) {
            try {
                fastaOutputStream = new PrintStream(outputDNAFASTA);
            } catch (FileNotFoundException fnfe) {
                throw new OutputException("No such file: " + outputDNAFASTA.getAbsolutePath(), fnfe);
            }
        }
        while (true) {
            ViterbiResult result = ThreadManager.getInstance().getNextOutputBlocking();

            if (result.isEOF()) {
                break;
            }

            // Write to fasta file
            if (fastaOutputStream != null) {
                result.writeFasta(fastaOutputStream);
            }

            // Write to stdout
            result.writeProteins(System.out);
        }
        if (fastaOutputStream != null) {
            fastaOutputStream.close();
        }
    }
}

package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.exceptions.InvalidInputException;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
import be.robbevanherck.javafraggenescan.entities.ViterbiResult;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Repository for in and output that needs to be blocking
 */
public class SynchronousRepository {
    private final BlockingQueue<ViterbiInput> inputQueue;
    private final BlockingQueue<ViterbiResult> outputQueue;
    private final AtomicBoolean processedAllInput;

    /**
     * Create an SynchronousRepository
     */
    public SynchronousRepository() {
        inputQueue = new LinkedBlockingDeque<>();
        outputQueue = new LinkedBlockingDeque<>();
        processedAllInput = new AtomicBoolean(false);

        try {
            System.err.println("Starting Read");
            for (Map.Entry<String, DNASequence> entry : FastaReaderHelper.readFastaDNASequence(System.in).entrySet()) {
                inputQueue.add(new ViterbiInput(entry.getKey(), dnaSequenceToViterbiInput(entry.getValue())));
            }
            processedAllInput.set(true);
            System.err.println("Done Reading");
        } catch (IOException e) {
            throw new InvalidInputException("Could not read input from stdin", e);
        }
    }

    private List<AminoAcid> dnaSequenceToViterbiInput(DNASequence value) {
        List<AminoAcid> ret = new ArrayList<>();
        for (NucleotideCompound aminoAcidCompound : value.getAsList()) {
            ret.add(AminoAcid.fromString(aminoAcidCompound.getShortName()));
        }
        return ret;
    }

    private static SynchronousRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new SynchronousRepository();
    }

    public static SynchronousRepository getInstance() {
        return instance;
    }

    public ViterbiInput getNextInput() throws InterruptedException {
        return inputQueue.take();
    }

    /**
     * Add a set of results
     * @param results The results that need to be added
     */
    public void putAllOutput(Set<ViterbiResult> results) {
        outputQueue.addAll(results);
    }

    /**
     * Return if the input queue has input to be processed
     * @return true if the input queue has input, false otherwise
     */
    public boolean hasNextInput() {
        return !inputQueue.isEmpty();
    }

    /**
     * Return if the output queue has output to be processed
     * @return true if the output queue has output, false otherwise
     */
    public boolean hasNextOutput() {
        return !outputQueue.isEmpty();
    }

    /**
     * Get the next output, blocking
     * @return The next output
     */
    public ViterbiResult getNextOutputBlocking() throws InterruptedException {
        return outputQueue.take();
    }

    /**
     * Return whether the reader thread has processed all input
     * @return true if the reader has processed all input, false otherwise
     */
    public boolean hasProcessedAllInput() {
        return processedAllInput.get();
    }
}

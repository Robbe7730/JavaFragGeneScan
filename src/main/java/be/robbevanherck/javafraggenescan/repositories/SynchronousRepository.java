package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.InvalidInputException;
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

/**
 * Repository for in and output that needs to be blocking
 */
public class SynchronousRepository {
    private final BlockingQueue<ViterbiInput> inputQueue;
    private final BlockingQueue<ViterbiResult> outputQueue;

    /**
     * Create an SynchronousRepository
     */
    public SynchronousRepository() {
        inputQueue = new LinkedBlockingDeque<>();
        outputQueue = new LinkedBlockingDeque<>();

        try {
            System.err.println("Starting Read");
            for (Map.Entry<String, DNASequence> entry : FastaReaderHelper.readFastaDNASequence(System.in).entrySet()) {
                inputQueue.add(new ViterbiInput(entry.getKey(), dnaSequenceToViterbiInput(entry.getValue())));
            }
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
     * Add a new result
     * @param result The result that needs to be added
     */
    public void putOutput(ViterbiResult result) {
        outputQueue.add(result);
    }

    public boolean isInputEmpty() {
        return inputQueue.isEmpty();
    }

    public boolean isOutputEmpty() {
        return outputQueue.isEmpty();
    }

    public ViterbiResult getNextOutput() throws InterruptedException {
        return outputQueue.take();
    }
}

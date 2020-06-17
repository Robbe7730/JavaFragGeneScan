package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.InvalidInputException;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
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
public class SyncInputRepository {
    private final BlockingQueue<ViterbiInput> queue;

    /**
     * Create an InputRepository
     */
    public SyncInputRepository() {
        queue = new LinkedBlockingDeque<>();

        try {
            System.err.println("Starting Read");
            for (Map.Entry<String, DNASequence> entry : FastaReaderHelper.readFastaDNASequence(System.in).entrySet()) {
                queue.add(new ViterbiInput(entry.getKey(), dnaSequenceToViterbiInput(entry.getValue())));
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

    private static SyncInputRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new SyncInputRepository();
    }

    public static SyncInputRepository getInstance() {
        return instance;
    }

    public ViterbiInput getNextInput() throws InterruptedException {
        return queue.take();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

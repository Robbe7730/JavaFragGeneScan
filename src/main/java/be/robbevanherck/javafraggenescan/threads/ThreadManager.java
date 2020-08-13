package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
import be.robbevanherck.javafraggenescan.entities.ViterbiResult;
import be.robbevanherck.javafraggenescan.exceptions.InvalidInputException;
import be.robbevanherck.javafraggenescan.exceptions.TooManyThreadsException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the Writer- and Runner-threads
 */
public class ThreadManager {
    private static ThreadManager instance;
    private final AtomicInteger numRunnerThreads;
    private final AtomicBoolean hasStarted;
    private final AtomicBoolean processedAllInput;
    private final BlockingQueue<ViterbiInput> inputQueue;
    private final BlockingQueue<ViterbiResult> outputQueue;

    private boolean wholeGenomes;
    private int numThreads;

    /**
     * Create a new ThreadManager
     */
    private ThreadManager() {
        numRunnerThreads = new AtomicInteger(0);
        hasStarted = new AtomicBoolean(false);
        processedAllInput = new AtomicBoolean(false);
        inputQueue = new LinkedBlockingDeque<>();
        outputQueue = new LinkedBlockingDeque<>();
    }

    public static ThreadManager getInstance() {
        return instance;
    }

    /**
     * Create a new instance
     */
    public static void createInstance() {
        instance = new ThreadManager();
    }

    /**
     * Start and run all threads
     * @param modelConfFile The file containing the model config
     * @param outputDNAFASTA The file where to output the DNA (or null)
     * @param inputType 1 if the input contains only full genomes, 0 otherwise
     * @param numThreads The maximum number of threads
     * @throws InterruptedException When the program gets interrupted while waiting
     */
    public void run(File modelConfFile, File outputDNAFASTA, int inputType, int numThreads) throws InterruptedException {
        this.wholeGenomes = inputType == 1;
        this.numThreads = numThreads;

        // Read in all the files
        HMMParameters.setup(modelConfFile);

        startReaderThread();

        WriterThreadRunnable writer = new WriterThreadRunnable(outputDNAFASTA);
        Thread writerThread = new Thread(writer);
        writerThread.setName("Writer Thread");
        writerThread.start();

        hasStarted.set(true);
        for (int threadCount = 0; threadCount < numThreads; threadCount++) {
            startRunnerThread();
        }
    }

    private void startRunnerThread() {
        if (this.processedAllInput.get() && this.isInputQueueEmpty()) {
            return;
        }
        if (numRunnerThreads.get() >= numThreads) {
            throw new TooManyThreadsException("Too many threads started by ThreadManager::startRunnerThread");
        }

        ViterbiInput input;
        try {
            // This is ok to be blocking, as we check if it has (or will get) new input in the if in the beginning
            // TODO: this is false, it can still break, add a semaphore
            input = this.getNextInputBlocking();
        } catch (InterruptedException interruptedException) {
            //TODO: handle interrupt
            return;
        }

        Thread runnerThread = new Thread(new RunnerThreadRunnable(wholeGenomes, input));
        runnerThread.setName(input.getName());
        numRunnerThreads.incrementAndGet();
        runnerThread.start();
    }

    /**
     * Notify the ThreadManager that a thread has finished and written its output to the output queue
     * @param result The result of the thread that finished
     */
    public void notifyFinished(Set<ViterbiResult> result) {
        outputQueue.addAll(result);
        numRunnerThreads.decrementAndGet();
        startRunnerThread();
    }

    /**
     * Check if the writer thread needs to stay alive
     * @return true if the writer thread has to stay alive, false otherwise
     */
    public boolean writerThreadAlive() {
        return !(processedAllInput.get() && isInputQueueEmpty() && numRunnerThreads.get() == 0 && isOutputQueueEmpty());
    }

    /**
     * Start the reader thread
     */
    public void startReaderThread() {
        // TODO: make this a thread
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

    /**
     * Get the next input from the input queue, blocking
     * @return The next input from the input queue
     * @throws InterruptedException if it gets interrupted
     */
    public ViterbiInput getNextInputBlocking() throws InterruptedException {
        return inputQueue.take();
    }

    /**
     * Return if the input queue has input to be processed
     * @return true if the input queue has input, false otherwise
     */
    public boolean isInputQueueEmpty() {
        return inputQueue.isEmpty();
    }

    /**
     * Return if the output queue has output to be processed
     * @return true if the output queue has output, false otherwise
     */
    public boolean isOutputQueueEmpty() {
        return outputQueue.isEmpty();
    }

    /**
     * Get the next output, blocking
     * @return The next output
     */
    public ViterbiResult getNextOutputBlocking() throws InterruptedException {
        return outputQueue.take();
    }
}

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
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the Writer- and Runner-threads
 */
public class ThreadManager {
    private static ThreadManager instance;
    private final AtomicInteger numRunnerThreads;
    private final AtomicBoolean processedAllInput;
    private final BlockingQueue<ViterbiInput> inputQueue;
    private final BlockingQueue<ViterbiResult> outputQueue;
    private final Semaphore nextInputSemaphore;

    private boolean wholeGenomes;

    /**
     * Create a new ThreadManager
     */
    private ThreadManager() {
        numRunnerThreads = new AtomicInteger(0);
        processedAllInput = new AtomicBoolean(false);
        inputQueue = new LinkedBlockingDeque<>();
        outputQueue = new LinkedBlockingDeque<>();
        nextInputSemaphore = new Semaphore(1);
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

        // Read in all the files
        HMMParameters.setup(modelConfFile);

        startReaderThread();

        Thread writerThread = new Thread(new WriterThreadRunnable(outputDNAFASTA));
        writerThread.setName("Writer Thread");
        writerThread.start();

        for (int threadCount = 0; threadCount < numThreads; threadCount++) {
            startRunnerThread();
        }
    }

    private void startRunnerThread() {
        numRunnerThreads.getAndIncrement();
        Thread runnerThread = new Thread(new RunnerThreadRunnable(wholeGenomes));
        runnerThread.start();
    }

    /**
     * Start the reader thread
     */
    public void startReaderThread() {
        Thread readerThread = new Thread(new ReaderThreadRunnable());
        readerThread.setName("Reader thread");
        readerThread.start();
    }

    /**
     * Get the next input from the input queue, blocking
     * @return The next input from the input queue or null if the thread can exit
     */
    public ViterbiInput getNextInputBlocking() {
        try {
            // Try to acquire the semaphore for 100 milliseconds, then check if the runner needs to stop and retry
            do {
                if (runnersStopping()) {
                    return null;
                }
            } while (!nextInputSemaphore.tryAcquire(100, TimeUnit.MILLISECONDS));
            ViterbiInput input = inputQueue.take();
            nextInputSemaphore.release();
            return input;
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Check if the writer thread needs to stay alive
     * @return true if the writer thread has to stop, false otherwise
     */
    public boolean writerStopping() {
        // Only kill the writer thread when all the threads have stopped and all output is processed
        return runnersStopping() && numRunnerThreads.get() == 0 && isOutputQueueEmpty();
    }
    /**
     * Check if the runner threads need to stay alive
     * @return true if the threads have to stop, false otherwise
     */
    private boolean runnersStopping() {
        // Only kill runners if the input is processed and the queue is empty
        return processedAllInput.get() && isInputQueueEmpty();
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

    /**
     * Notify the ThreadManager that a thread is going to stop
     */
    public void notifyStoppingThread() {
        numRunnerThreads.getAndDecrement();
    }

    /**
     * Add a set of results to the output queue
     * @param results The results to add
     */
    public void addToOutput(Set<ViterbiResult> results) {
        outputQueue.addAll(results);
    }

    /**
     * Add a ViterbiInput to the input queue
     * @param viterbiInput The input to add
     */
    public void addToInput(ViterbiInput viterbiInput) {
        inputQueue.add(viterbiInput);
    }

    /**
     * Notify the ThreadManager that the input is fully processed
     */
    public void setInputProcessed() {
        processedAllInput.set(true);
    }
}

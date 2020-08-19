package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.entities.*;

import java.io.File;
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
    private final AtomicBoolean processedAllInput;
    private final BlockingQueue<ViterbiInput> inputQueue;
    private final BlockingQueue<ViterbiResult> outputQueue;

    private boolean wholeGenomes;

    /**
     * Create a new ThreadManager
     */
    private ThreadManager() {
        numRunnerThreads = new AtomicInteger(0);
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
     */
    public void run(File modelConfFile, File outputDNAFASTA, int inputType, int numThreads) {
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


        try {
            // Since the writerThread is the last one to exit, join that one
            writerThread.join();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
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
     * @return The next input from the input queue or an EOFViterbiInput if the thread can exit
     */
    public ViterbiInput getNextInputBlocking() {
        try {
            ViterbiInput input = inputQueue.take();
            if (input.isEOF()) {
                inputQueue.add(input);
            }
            return input;
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return new EOFViterbiInput();
        }
    }

    /**
     * Get the next output, blocking
     * @return The next output or an EOFViterbiResult when the thread can stop
     */
    public ViterbiResult getNextOutputBlocking() {
        try {
            this.checkWriterStopping();
            return outputQueue.take();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            return new EOFViterbiResult();
        }
    }

    /**
     * Check to see if the sentinel EOFViterbiResult needs to be added to the output queue
     */
    private void checkWriterStopping() {
        // If stdin is read, the input queue only has the EOF sentinel, all threads have stopped and the output queue is empty
        // we can stop the writer thread
        if (processedAllInput.get() &&
                (!inputQueue.isEmpty() &&
                 inputQueue.peek().isEOF()
                ) &&
                numRunnerThreads.get() == 0 &&
                outputQueue.isEmpty()
        ) {
            outputQueue.add(new EOFViterbiResult());
        }
    }

    /**
     * Notify the ThreadManager that a thread is going to stop
     */
    public void notifyStoppingThread() {
        numRunnerThreads.getAndDecrement();
        this.checkWriterStopping();
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
        inputQueue.add(new EOFViterbiInput());
    }
}

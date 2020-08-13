package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
import be.robbevanherck.javafraggenescan.exceptions.TooManyThreadsException;
import be.robbevanherck.javafraggenescan.repositories.SynchronousRepository;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the Writer- and Runner-threads
 */
public class ThreadManager {
    private static ThreadManager instance;
    private final AtomicInteger numRunnerThreads;
    private final AtomicBoolean hasStarted;
    private boolean wholeGenomes;
    private int numThreads;

    /**
     * Create a new ThreadManager
     */
    private ThreadManager() {
        numRunnerThreads = new AtomicInteger(0);
        hasStarted = new AtomicBoolean(false);
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

        // TODO: make this a reader thread
        SynchronousRepository.createInstance();

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
        if (SynchronousRepository.getInstance().hasProcessedAllInput() && !SynchronousRepository.getInstance().hasNextInput()) {
            return;
        }
        if (numRunnerThreads.get() >= numThreads) {
            throw new TooManyThreadsException("Too many threads started by ThreadManager::startRunnerThread");
        }

        ViterbiInput input;
        try {
            input = SynchronousRepository.getInstance().getNextInput();
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
     * @param runnerThreadRunnable The thread that finished
     */
    public void notifyFinished(RunnerThreadRunnable runnerThreadRunnable) {
        numRunnerThreads.decrementAndGet();
        startRunnerThread();
    }

    /**
     * Check if the writer thread needs to stay alive
     * @return true if the writer thread has to stay alive, false otherwise
     */
    public boolean writerThreadAlive() {
        return !SynchronousRepository.getInstance().hasProcessedAllInput() || numRunnerThreads.get() != 0;
    }
}

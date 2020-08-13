package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
import be.robbevanherck.javafraggenescan.repositories.SynchronousRepository;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the Writer- and Runner-threads
 */
public class ThreadManager {
    private static ThreadManager instance;
    private AtomicInteger numRunnerThreads;
    private AtomicBoolean hasStarted;

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
        // Read in all the files
        HMMParameters.setup(modelConfFile);

        // TODO: make this a reader thread
        SynchronousRepository.createInstance();

        WriterThreadRunnable writer = new WriterThreadRunnable(outputDNAFASTA);
        Thread writerThread = new Thread(writer);
        writerThread.setName("Writer Thread");
        writerThread.start();

        hasStarted.set(true);
        while(SynchronousRepository.getInstance().hasNextInput()) {
            while (numRunnerThreads.get() >= numThreads) {}
            startRunnerThread(inputType == 1);
        }
    }

    private void startRunnerThread(boolean wholeGenomes) throws InterruptedException {
        ViterbiInput input = SynchronousRepository.getInstance().getNextInput();

        RunnerThreadRunnable runner = new RunnerThreadRunnable(wholeGenomes, input);
        Thread runnerThread = new Thread(runner);
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
    }

    /**
     * Check if the writer thread needs to stay alive
     * @return true if the writer thread has to stay alive, false otherwise
     */
    public boolean writerThreadAlive() {
        return !hasStarted.get() || numRunnerThreads.get() != 0 || SynchronousRepository.getInstance().hasNextOutput() || SynchronousRepository.getInstance().hasNextInput();
    }
}

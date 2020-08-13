package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.ViterbiAlgorithm;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;

/**
 * Runnable for the calculating (Running) thread
 */
public class RunnerThreadRunnable implements Runnable {

    private final boolean wholeGenomes;
    private final ViterbiInput input;

    /**
     * Create a new RunnerThread
     * @param wholeGenomes true if the input contains whole genomes, false otherwise
     * @param input The input to process
     */
    public RunnerThreadRunnable(boolean wholeGenomes, ViterbiInput input) {
        this.wholeGenomes = wholeGenomes;
        this.input = input;
    }

    @Override
    public void run() {
        int inputLength = input.getInputAcids().size();

        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, wholeGenomes);
        ThreadManager.getInstance().notifyFinished(algorithm.backTrack(algorithm.run(), inputLength));
    }
}

package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.ViterbiAlgorithm;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;

/**
 * Runnable for the calculating (Running) thread
 */
public class RunnerThreadRunnable implements Runnable {

    private final boolean wholeGenomes;

    /**
     * Create a new RunnerThread
     * @param wholeGenomes true if the input contains whole genomes, false otherwise
     */
    public RunnerThreadRunnable(boolean wholeGenomes) {
        this.wholeGenomes = wholeGenomes;
    }

    @Override
    public void run() {
        while (true) {
            ViterbiInput input = ThreadManager.getInstance().getNextInputBlocking();
            if (input == null) {
                ThreadManager.getInstance().notifyStoppingThread();
                return;
            }
            int inputLength = input.getInputAcids().size();

            ViterbiAlgorithm algorithm = new ViterbiAlgorithm(input, wholeGenomes);
            ThreadManager.getInstance().addToOutput(algorithm.backTrack(algorithm.run(), inputLength));
        }
    }
}

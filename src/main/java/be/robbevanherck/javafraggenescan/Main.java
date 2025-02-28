package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.threads.ThreadManager;
import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.*;

/**
 * The entry class of the project
 */
public class Main {
    /**
     * ParameterValidator for the -p/--num-threads parameter
     */
    public static class ThreadValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) {
            try {
                if (Integer.parseInt(value) <= 0) {
                    throw new ParameterException("Number of threads must be greater than 0");
                }
            } catch (NumberFormatException nfe) {
                throw new ParameterException("Invalid number of threads", nfe);
            }
        }
    }

    @Parameter(names={"-w", "--input-type"}, description = "0 for short sequence reads or 1 for full genome sequences")
    private int inputType;

    @Parameter(names={"-p", "--num-threads"}, description = "The number of runner threads to use", validateWith = ThreadValidator.class)
    private int numThreads = 1;

    @Parameter(names={"-t", "--model-parameters"}, description = "File with the model parameters", required = true)
    private File modelConfFile;

    @Parameter(names={"-d", "--dna-file"}, description = "Output file for the DNA results. If not present, no output is written")
    private File outputDNAFASTA;

    @Parameter(names={"-h" , "-?", "--help", "--usage"}, description = "Show this help text and exit", help = true)
    private boolean help;

    /**
     * The entry function
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(main)
                .build();
        jCommander.parse(args);

        if (main.help) {
            jCommander.usage();
            return;
        }
        main.run();
    }

    /**
     * Run the program itself
     */
    public void run() {
        long startTime = System.nanoTime();
        ThreadManager.createInstance();
        ThreadManager.getInstance().run(modelConfFile, outputDNAFASTA, inputType, numThreads);
        long endTime = System.nanoTime();
        System.err.println("Execution took " + ((endTime - startTime) / 1000000000.0) + " seconds");
    }
}

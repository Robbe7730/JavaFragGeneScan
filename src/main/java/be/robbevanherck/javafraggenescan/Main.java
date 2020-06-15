package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * ParameterValidator for the -l/--log-level parameter
     */
    public static class LogLevelValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) {
            try {
                if (Integer.parseInt(value) < 0) {
                    throw new ParameterException("Log level must be greater than or equal to 0");
                }
                if (Integer.parseInt(value) > 4) {
                    throw new ParameterException("Log level must be less than 5");
                }
            } catch (NumberFormatException nfe) {
                throw new ParameterException("Invalid loglevel", nfe);
            }
        }
    }

    @Parameter(names={"-w", "--input-type"}, description = "0 for short sequence reads or 1 for full genome sequences")
    private int inputType;

    @Parameter(names={"-p", "--num-threads"}, description = "The number of threads to use", validateWith = ThreadValidator.class)
    private int numThreads = 1;

    @Parameter(names={"-t", "--model-parameters"}, description = "File with the model parameters", required = true)
    private File modelConfFile;

    @Parameter(names={"-d", "--output-dna-fasta"}, description = "Output file for the DNA-FASTA. If not present, no output is written")
    private File outputDNAFASTA;

    @Parameter(names={"-e", "--output-metadata"}, description = "Output file for the metadata. If not present, no output is written")
    private File outputMetaData;

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
        //TODO This needs to be parallelized and use actual data
        List<AminoAcid> input = new LinkedList<>(List.of(
                AminoAcid.A,
                AminoAcid.C,
                AminoAcid.G,
                AminoAcid.T
        ));
        // The amount of times G or C occurs in the input
        int countGC = Collections.frequency(input, AminoAcid.C) + Collections.frequency(input, AminoAcid.G);

        // The percentage
        countGC = ((countGC * 100) / input.size());

        // Create the parameters
        HMMParameters parameters = new HMMParameters(countGC, inputType == 1);

        ViterbiAlgorithm algorithm = new ViterbiAlgorithm(parameters);
        algorithm.run(input);
    }
}

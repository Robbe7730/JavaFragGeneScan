package be.robbevanherck.javafraggenescan;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;

/**
 * The entry class of the project
 */
public class Main {
    @Parameter(names={"-w", "--input-type"}, description = "0 for short sequence reads or 1 for full genome sequences")
    private int inputType;

    @Parameter(names={"-p", "--num-threads"}, description = "The number of threads to use")
    private int numThreads = 1;

    @Parameter(names={"-t", "--model-parameters"}, description = "File with the model parameters", required = true)
    private File modelParamFile;

    @Parameter(names={"-d", "--output-dna-fasta"}, description = "Output file for the DNA-FASTA. If not present, no output is written")
    private File outputDNAFASTA;

    @Parameter(names={"-e", "--output-metadata"}, description = "Output file for the metadata. If not present, no output is written")
    private File outputMetaData;

    @Parameter(names = {"-h" , "-?", "--help", "--usage"}, description = "Show this help text and exit", help = true)
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
        System.out.printf("Got parameters: inputType = %d, numThreads = %d, modelParamFile = %s%n", inputType, numThreads, modelParamFile.getAbsolutePath());
        if (outputDNAFASTA != null) {
            System.out.printf("Also got DNA-FASTA output file %s%n", outputDNAFASTA.getAbsolutePath());
        }
        if (outputMetaData != null) {
            System.out.printf("Also got metadata output file %s%n", outputMetaData.getAbsolutePath());
        }
    }
}

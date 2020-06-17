package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.DNAUtil;
import be.robbevanherck.javafraggenescan.OutputException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing the result of the Viterbi algorithm
 */
public class ViterbiResult {
    private final List<AminoAcid> result;
    private final int start;
    private final int stop;
    private final DNAStrand strand;
    private final String name;

    /**
     * Create a new ViterbiResult
     * @param result The resulting DNA sequence
     * @param start The start index
     * @param stop The end index
     * @param strand  Which strand this result came from
     * @param name The header in the input fasta file
     */
    public ViterbiResult(List<AminoAcid> result, int start, int stop, DNAStrand strand, String name) {
        this.result = result;
        this.start = start;
        this.stop = stop;
        this.strand = strand;
        this.name = name;
    }

    public String getDNA() {
        return result.stream().map(AminoAcid::toString).collect(Collectors.joining(""));
    }

    public String getProteins() {
        return DNAUtil.getProteins(result, strand);
    }

    public String getHeaderSuffix() {
        return "_" + start + "_" + stop + "_" + (strand == DNAStrand.REVERSE ? "-" : "+");
    }

    /**
     * Write the result to a fasta file
     * @param fileWriter The FileWriter to write to
     * @throws IOException When something goes wrong
     */
    public void writeToFasta(FileWriter fileWriter) throws IOException {
        fileWriter.append(">")
                .append(name)
                .append(getHeaderSuffix())
                .append('\n')
                .append(getDNA())
                .append('\n')
                .flush();
    }
}

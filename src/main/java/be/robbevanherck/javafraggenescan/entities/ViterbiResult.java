package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.DNAUtil;

import java.io.PrintStream;
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
        return DNAUtil.getProteins(result);
    }

    public String getHeaderSuffix() {
        String strandCharacter;
        switch (strand) {
            case FORWARD:
                strandCharacter = "+";
                break;
            case REVERSE:
                strandCharacter = "-";
                break;
            default:
                strandCharacter = "U";
                break;
        }
        return "_" + start + "_" + stop + "_" + strandCharacter;
    }

    /**
     * Write the DNA sequence to a fasta file
     * @param printStream The PrintStream to write to
     */
    public void writeFasta(PrintStream printStream) {
        writeToPrintStream(printStream, getDNA());
    }

    /**
     * Write the protein sequence to a fasta file
     * @param printStream The PrintStream to write to
     */
    public void writeProteins(PrintStream printStream) {
        writeToPrintStream(printStream, getProteins());
    }

    private void writeToPrintStream(PrintStream printStream, String content) {
        printStream.append(">")
                .append(name)
                .append(getHeaderSuffix())
                .append('\n')
                .append(content)
                .append('\n')
                .flush();
    }

    /**
     * Indicates if this ViterbiResult is an actual input or an EOFViterbiResult
     * @return false for ViterbiResult, true for EOFViterbiResult
     */
    public boolean isEOF() {
        return false;
    }
}

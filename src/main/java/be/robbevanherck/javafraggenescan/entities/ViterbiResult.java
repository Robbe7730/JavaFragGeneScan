package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.DNAUtil;

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

    /**
     * Create a new ViterbiResult
     * @param result The resulting DNA sequence
     * @param start The start index
     * @param stop The end index
     * @param strand  Which strand this result came from
     */
    public ViterbiResult(List<AminoAcid> result, int start, int stop, DNAStrand strand) {
        this.result = result;
        this.start = start;
        this.stop = stop;
        this.strand = strand;
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
}

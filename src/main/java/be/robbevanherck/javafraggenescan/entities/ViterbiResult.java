package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.DNAToProteinUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing the result of the Viterbi algorithm
 */
public class ViterbiResult {
    private final List<AminoAcid> result;
    private final int start;
    private final int stop;
    private final boolean isReverseStrand;

    /**
     * Create a new ViterbiResult
     * @param result The result
     * @param start The start index
     * @param stop The end index
     * @param isReverseStrand  If this is the reverse strand
     */
    public ViterbiResult(List<AminoAcid> result, int start, int stop, boolean isReverseStrand) {
        this.result = result;
        this.start = start;
        this.stop = stop;
        this.isReverseStrand = isReverseStrand;
    }

    public String getDNA() {
        return result.stream().map(AminoAcid::toString).collect(Collectors.joining());
    }

    public String getProteins() {
        return DNAToProteinUtil.getProteins(result, isReverseStrand);
    }

    public String getHeaderSuffix() {
        return "_" + start + "_" + stop + "_" + (isReverseStrand ? "-" : "+");
    }
}

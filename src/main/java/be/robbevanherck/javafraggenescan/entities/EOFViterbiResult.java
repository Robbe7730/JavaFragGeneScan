package be.robbevanherck.javafraggenescan.entities;

import java.util.List;

/**
 * The sentinel ViterbiResult indicating that the output is done and the writer thread can stop
 */
public class EOFViterbiResult extends ViterbiResult {

    /**
     * Create a new ViterbiResult
     */
    public EOFViterbiResult() {
        super(List.of(), 0, 0, DNAStrand.UNKNOWN_STRAND, "EOF");
    }

    /**
     * Indicates if this ViterbiResult is an actual input or an EOFViterbiResult
     * @return false for ViterbiResult, true for EOFViterbiResult
     */
    public boolean isEOF() {
        return true;
    }
}

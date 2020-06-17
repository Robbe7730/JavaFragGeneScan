package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.Triple;

/**
 * Class with utility functions for Start/Stop codons
 */
public class StartStopUtil {
    private StartStopUtil() {}

    /**
     * Check if the codon is a reverse stop codon
     * @param codon The codon
     * @return true if it's a reverse stop codon, false otherwise
     */
    public static boolean isReverseStopCodon(Triple<AminoAcid> codon) {
        return codon != null &&
                (codon.getFirstValue() == AminoAcid.C && codon.getSecondValue() == AminoAcid.A && (             // The first 2 acids are always CA
                        (codon.getThirdValue() == AminoAcid.T) ||                                               // CAT
                        (codon.getThirdValue() == AminoAcid.C) ||                                               // CAC
                        (codon.getThirdValue() == AminoAcid.A)                                                  // CAA
                ));
    }

    /**
     * Check if the codon is a forward stop codon
     * @param codon The codon
     * @return true if it's a forward stop codon, false otherwise
     */
    public static boolean isForwardStopCodon(Triple<AminoAcid> codon) {
        return codon != null &&
                    (codon.getFirstValue() == AminoAcid.T && (                                                    // The first acid is always T
                            (codon.getSecondValue() == AminoAcid.A && codon.getThirdValue() == AminoAcid.A) ||    // TAA
                            (codon.getSecondValue() == AminoAcid.A && codon.getThirdValue() == AminoAcid.G) ||    // TAG
                            (codon.getSecondValue() == AminoAcid.G && codon.getThirdValue() == AminoAcid.A)       // TGA
                    ));
    }

    /**
     * Check if the codon is a reverse start codon
     * @param codon The codon
     * @return true if it's a reverse start codon, false otherwise
     */
    public static boolean isReverseStartCodon(Triple<AminoAcid> codon) {
        return codon != null &&
                (codon.getThirdValue() == AminoAcid.A && (                                                    // The first acid is always A
                        (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.T) ||    // TTA
                        (codon.getFirstValue() == AminoAcid.T && codon.getSecondValue() == AminoAcid.C) ||    // TCA
                        (codon.getFirstValue() == AminoAcid.C && codon.getSecondValue() == AminoAcid.T)       // CTA
                ));
    }
    /**
     * Check if the codon is a forward start codon
     * @param codon The codon
     * @return true if it's a forward start codon, false otherwise
     */
    public static boolean isForwardStartCodon(Triple<AminoAcid> codon) {
        return codon != null &&
                (codon.getThirdValue() == AminoAcid.G && codon.getSecondValue() == AminoAcid.T && (             // The last 2 acids are always TG
                        (codon.getFirstValue() == AminoAcid.A) ||                                               // ATG
                        (codon.getFirstValue() == AminoAcid.G) ||                                               // GTG
                        (codon.getFirstValue() == AminoAcid.T)                                                  // TTG
                ));
    }
}

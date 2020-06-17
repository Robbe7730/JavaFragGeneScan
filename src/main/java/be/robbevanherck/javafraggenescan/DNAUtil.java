package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.DNAStrand;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts DNA to proteins
 */
public class DNAUtil {
    private DNAUtil() {}

    private static final char[] TRANSLATION_TABLE_11 = {
        'K', 'N', 'K', 'N',
        'T', 'T', 'T', 'T',
        'R', 'S', 'R', 'S',
        'I', 'I', 'M', 'I',

        'Q', 'H', 'Q', 'H',
        'P', 'P', 'P', 'P',
        'R', 'R', 'R', 'R',
        'L', 'L', 'L', 'L',

        'E', 'D', 'E', 'D',
        'A', 'A', 'A', 'A',
        'G', 'G', 'G', 'G',
        'V', 'V', 'V', 'V',

        '*', 'Y', '*', 'Y',
        'S', 'S', 'S', 'S',
        '*', 'C', 'W', 'C',
        'L', 'F', 'L', 'F'
    };

    /**
     * Get the protein string for the reverse strand
     * @param acidList The amino-acids
     * @param strand Which strand it comes from
     * @return The string of proteins
     */
    public static String getProteins(List<AminoAcid> acidList, DNAStrand strand) {
        Iterator<AminoAcid> acidIterator = acidList.iterator();

        List<Character> ret = new LinkedList<>();

        while(acidIterator.hasNext()) {
            ret.add(TRANSLATION_TABLE_11[trinucleotideToInt(acidIterator.next(), acidIterator.next(), acidIterator.next())]);
        }

        return ret.stream().map(String::valueOf).collect(Collectors.joining());
    }

    private static int trinucleotideToInt(AminoAcid firstAcid, AminoAcid secondAcid, AminoAcid thirdAcid) {
        return (AminoAcid.toInt(firstAcid) << 4 | AminoAcid.toInt(secondAcid) << 2 | AminoAcid.toInt(thirdAcid));
    }

    /**
     * Get the complement of a DNA strand
     * @param strand The strand
     * @return The complement
     */
    public static List<AminoAcid> complement(List<AminoAcid> strand) {
        List<AminoAcid> newAcids = new LinkedList<>();
        for (AminoAcid acid : strand) {
            newAcids.add(complement(acid));
        }
        return newAcids;
    }

    /**
     * Return the complement of a single amino-acid
     * @param acid The amino-acid
     * @return the complement
     */
    public static AminoAcid complement(AminoAcid acid) {
        switch (acid) {
            case A:
                return AminoAcid.T;
            case C:
                return AminoAcid.G;
            case G:
                return AminoAcid.C;
            case T:
                return AminoAcid.A;
            default:
                return AminoAcid.INVALID;
        }
    }
}
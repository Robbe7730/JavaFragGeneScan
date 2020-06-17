package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts DNA to proteins
 */
public class DNAToProteinUtil {
    private DNAToProteinUtil() {}

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

    private static final char[] TRANSLATION_TABLE_11_RC = {
        'F', 'V', 'L', 'I',
        'C', 'G', 'R', 'S',
        'S', 'A', 'P', 'T',
        'Y', 'D', 'H', 'N',

        'L', 'V', 'L', 'M',
        'W', 'G', 'R', 'R',
        'S', 'A', 'P', 'T',
        '*', 'E', 'Q', 'K',

        'F', 'V', 'L', 'I',
        'C', 'G', 'R', 'S',
        'S', 'A', 'P', 'T',
        'Y', 'D', 'H', 'N',

        'L', 'V', 'L', 'I',
        '*', 'G', 'R', 'R',
        'S', 'A', 'P', 'T',
        '*', 'E', 'Q', 'K'
    };

    /**
     * Get the protein string for the reverse strand
     * @param acidList The amino-acids
     * @param isReverse If the DNA comes from the reverse strand
     * @return The string of proteins
     */
    public static String getProteins(List<AminoAcid> acidList, boolean isReverse) {
        Iterator<AminoAcid> acidIterator = acidList.iterator();

        List<Character> ret = new LinkedList<>();

        char[] translationTable = isReverse ? TRANSLATION_TABLE_11_RC : TRANSLATION_TABLE_11;

        while(acidIterator.hasNext()) {
            ret.add(translationTable[trinucleotideToInt(acidIterator.next(), acidIterator.next(), acidIterator.next())]);
        }

        return ret.stream().map(String::valueOf).collect(Collectors.joining());
    }

    private static int trinucleotideToInt(AminoAcid firstAcid, AminoAcid secondAcid, AminoAcid thirdAcid) {
        return (AminoAcid.toInt(firstAcid) << 4 | AminoAcid.toInt(secondAcid) << 2 | AminoAcid.toInt(thirdAcid));
    }
}

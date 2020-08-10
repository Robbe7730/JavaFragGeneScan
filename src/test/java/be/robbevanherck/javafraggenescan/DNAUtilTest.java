package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class DNAUtilTest {

    @Test
    public void getProteins() {
        // Test empty list
        assertEquals(0, DNAUtil.getProteins(Collections.emptyList()).length());

        // Test all the combinations in a big list, this includes INVALID acids
        List<AminoAcid> allAcids = new ArrayList<>();
        for (AminoAcid firstAcid : AminoAcid.values()) {
            for (AminoAcid secondAcid : AminoAcid.values()) {
                for (AminoAcid thirdAcid : AminoAcid.values()) {
                    allAcids.addAll(List.of(firstAcid, secondAcid, thirdAcid));
                }
            }
        }
        assertEquals(
                "KNKN*TTTT*RSRS*IIMI******QHQH*PPPP*RRRR*LLLL******EDED*AAAA*GGGG*VVVV*******Y*Y*SSSS**CWC*LFLF*******************************",
                DNAUtil.getProteins(allAcids)
        );
    }

    @Test
    public void singleComplement() {
        // Test the complements
        assertEquals(AminoAcid.T, DNAUtil.complement(AminoAcid.A));
        assertEquals(AminoAcid.A, DNAUtil.complement(AminoAcid.T));
        assertEquals(AminoAcid.G, DNAUtil.complement(AminoAcid.C));
        assertEquals(AminoAcid.C, DNAUtil.complement(AminoAcid.G));

        // Test the INVALID acid
        assertEquals(AminoAcid.INVALID, DNAUtil.complement(AminoAcid.INVALID));
    }

    @Test
    public void strandComplement() {
        // Test the empty list
        assertEquals(0, DNAUtil.complement(Collections.emptyList()).size());

        // Test a list containing all acids
        List<AminoAcid> strand = List.of(
                AminoAcid.A,
                AminoAcid.T,
                AminoAcid.G,
                AminoAcid.C,
                AminoAcid.INVALID,
                AminoAcid.C,
                AminoAcid.G,
                AminoAcid.T,
                AminoAcid.A
        );
        assertEquals(List.of(
                AminoAcid.T,
                AminoAcid.A,
                AminoAcid.C,
                AminoAcid.G,
                AminoAcid.INVALID,
                AminoAcid.G,
                AminoAcid.C,
                AminoAcid.A,
                AminoAcid.T
        ), DNAUtil.complement(strand));
    }
}
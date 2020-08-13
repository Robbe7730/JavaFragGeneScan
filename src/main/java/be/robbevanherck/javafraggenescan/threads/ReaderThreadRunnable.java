package be.robbevanherck.javafraggenescan.threads;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.ViterbiInput;
import be.robbevanherck.javafraggenescan.exceptions.InvalidInputException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Runnable for the Reader thread
 */
public class ReaderThreadRunnable implements Runnable {
    @Override
    public void run() {
        try {
            for (Map.Entry<String, DNASequence> entry : FastaReaderHelper.readFastaDNASequence(System.in).entrySet()) {
                ThreadManager.getInstance().addToInput(new ViterbiInput(entry.getKey(), dnaSequenceToViterbiInput(entry.getValue())));
            }
            ThreadManager.getInstance().setInputProcessed();
        } catch (IOException e) {
            throw new InvalidInputException("Could not read input from stdin", e);
        }
    }

    /**
     * Make a BioJava DNASequence into a list of AminoAcids
     * @param value The DNASequence
     * @return The list of AminoAcids
     */
    private List<AminoAcid> dnaSequenceToViterbiInput(DNASequence value) {
        List<AminoAcid> ret = new ArrayList<>();
        for (NucleotideCompound aminoAcidCompound : value.getAsList()) {
            ret.add(AminoAcid.fromString(aminoAcidCompound.getShortName()));
        }
        return ret;
    }
}

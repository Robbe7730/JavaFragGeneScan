package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.*;
import be.robbevanherck.javafraggenescan.transitions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Main class for everything related to the Viterbi algorithm
 */
public class ViterbiAlgorithm {
    private final ViterbiInput input;
    HMMParameters parameters;

    public static final List<Transition> TRANSITIONS = List.of(
            // M state
            new MatchForwardFirstTransition(),                         // M1
            new MatchForwardTransition(HMMState.MATCH_2),              // M2
            new MatchForwardTransition(HMMState.MATCH_3),              // M3
            new MatchForwardTransition(HMMState.MATCH_4),              // M4
            new MatchForwardTransition(HMMState.MATCH_5),              // M5
            new MatchForwardTransition(HMMState.MATCH_6),              // M6

            // I state
            new InsertForwardTransition(HMMState.INSERT_1),            // I1
            new InsertForwardTransition(HMMState.INSERT_2),            // I2
            new InsertForwardTransition(HMMState.INSERT_3),            // I3
            new InsertForwardTransition(HMMState.INSERT_4),            // I4
            new InsertForwardTransition(HMMState.INSERT_5),            // I5
            new InsertForwardSixthTransition(),                        // I6

            // M' state
            new MatchReverseFirstTransition(),                         // M1'
            new MatchReverseTransition(HMMState.MATCH_REVERSE_2),      // M2'
            new MatchReverseTransition(HMMState.MATCH_REVERSE_3),      // M3'
            new MatchReverseTransition(HMMState.MATCH_REVERSE_4),      // M4'
            new MatchReverseTransition(HMMState.MATCH_REVERSE_5),      // M5'
            new MatchReverseTransition(HMMState.MATCH_REVERSE_6),      // M6'

            // I' state
            new InsertReverseTransition(HMMState.INSERT_REVERSE_1),    // I1'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_2),    // I2'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_3),    // I3'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_4),    // I4'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_5),    // I5'
            new InsertReverseSixthTransition(),                        // I6'

            // R state (non-coding)
            new NonCodingTransition(),                                 // R

            // End state
            new EndForwardTransition(),                                // E

            // Start' state
            new StartReverseTransition(),                              // S'

            // Start state
            new StartForwardTransition(),                              // S

            // End' state
            new EndReverseTransition()                                 // E'
    );

    /**
     * Create a new ViterbiAlgorithm
     * @param input The input for this calculation
     * @param wholeGenome If the input are whole genomes or partial genomes
     */
    public ViterbiAlgorithm(ViterbiInput input, boolean wholeGenome) {
        List<AminoAcid> acidList = input.getInputAcids();

        // The amount of times G or C occurs in the input
        int countGC = Collections.frequency(acidList, AminoAcid.C) + Collections.frequency(acidList, AminoAcid.G);

        // The percentage
        countGC = ((countGC * 100) / acidList.size());

        // Create the parameters
        this.parameters = new HMMParameters(countGC, wholeGenome);
        this.input = input;
    }

    /**
     * Run the entire algorithm, clears the state when completed
     * @return The last step, which can be used for backtracking
     */
    public ViterbiStep run() {
        ViterbiStep currentStep = new ViterbiStep(parameters, input.getInputAcids());
        for (int i = 0; i < input.getInputAcids().size(); i++) {
            currentStep = currentStep.calculateNext();
        }
        return currentStep;
    }

    /**
     * Backtrack to find the result of the algorithm
     * @param currentStep The final step
     * @param sequenceLength The length of the input sequence
     * @return A set of ViterbiResults to be written
     */
    public Set<ViterbiResult> backTrack(ViterbiStep currentStep, int sequenceLength) {
        HMMState lastMatchingState = HMMState.NO_STATE;
        DNAStrand strand = DNAStrand.UNKNOWN_STRAND;

        HMMState currentState = currentStep.getHighestProbabilityState();
        PathProbability currentPathProbability = currentStep.getPathProbabilityFor(currentState);

        Set<ViterbiResult> ret = new HashSet<>();
        List<AminoAcid> currentDNAString = new LinkedList<>();

        int t = 0;

        // When we started matching or -1 if not matching
        int lastEnding = -1;

        // Backtrack until we hit an invalid state
        while (currentPathProbability.getPreviousState() != HMMState.NO_STATE) {

            if (HMMState.isForwardMatchState(currentState)) {
                /* START A FORWARD SEQUENCE */

                //TODO put this with the M' states in a function

                // If we weren't matching and are in an M3/M6 state, begin matching now
                if (strand == DNAStrand.UNKNOWN_STRAND && (
                        currentState == HMMState.MATCH_6 ||
                        currentState == HMMState.MATCH_3
                    )) {

                    strand = DNAStrand.FORWARD;
                    lastEnding = t;
                }

                if (strand == DNAStrand.FORWARD) {
                    currentDNAString.add(currentStep.getInput());
                    lastMatchingState = currentState;
                }

            } else if (HMMState.isReverseMatchState(currentState)) {
                /* START A REVERSE SEQUENCE */

                // If we weren't matching and are in an M3'/M6' state, begin matching now
                if (strand == DNAStrand.UNKNOWN_STRAND && (
                        currentState == HMMState.MATCH_REVERSE_6 ||
                        currentState == HMMState.MATCH_REVERSE_3
                )) {

                    strand = DNAStrand.REVERSE;
                    lastEnding = t;
                }

                if (strand == DNAStrand.REVERSE) {
                    currentDNAString.add(currentStep.getInput());
                    lastMatchingState = currentState;
                }
            } else if(currentState == HMMState.END_REVERSE || currentState == HMMState.END) {
                /* STORE THE LAST ENDING POSITION */
                lastEnding = t;
            } else if (currentState == HMMState.START || currentState == HMMState.START_REVERSE) {
                /* END THE SEQUENCE */

                // Add the result and reset values
                if (strand != DNAStrand.UNKNOWN_STRAND) {
                    addToResult(ret, currentDNAString, sequenceLength - t + 1, sequenceLength - lastEnding, strand, lastMatchingState);
                    currentDNAString = new LinkedList<>();
                }
                lastEnding = -1;
                strand = DNAStrand.UNKNOWN_STRAND;
            }
            // Find the next step
            currentStep = currentStep.getPrevious();
            currentState = currentPathProbability.getPreviousState();
            currentPathProbability = currentStep.getPathProbabilityFor(currentState);
            t++;
        }
        // Make sure to add remaining DNA strands
        if (strand != DNAStrand.UNKNOWN_STRAND) {
            addToResult(ret, currentDNAString, 1, sequenceLength - lastEnding, strand, lastMatchingState);
        }

        return ret;
    }

    private void addToResult(Set<ViterbiResult> ret, List<AminoAcid> currentDNAString, int start, int end, DNAStrand strand, HMMState lastMatchingState) {
        if (currentDNAString.isEmpty()) {
            return;
        }

        LinkedList<AminoAcid> currentDNAStringLL = (LinkedList<AminoAcid>) currentDNAString;

        // Remove trailing matches
        while (lastMatchingState != HMMState.NO_STATE &&
                lastMatchingState != HMMState.MATCH_1 &&
                lastMatchingState != HMMState.MATCH_4 &&
                lastMatchingState != HMMState.MATCH_REVERSE_1 &&
                lastMatchingState != HMMState.MATCH_REVERSE_4
        ) {
            currentDNAStringLL.removeLast();
            lastMatchingState = HMMState.nextState(lastMatchingState);
            start++;
        }

        List<AminoAcid> reversedCurrentDNAString;

        // To avoid reversing twice, we only reverse on the forward strand an only calculate the complement on the reverse strand
        if (strand == DNAStrand.REVERSE) {
            reversedCurrentDNAString = DNAUtil.complement(currentDNAStringLL);
        } else {
            // Reverse the string
            reversedCurrentDNAString = new LinkedList<>();
            currentDNAStringLL.descendingIterator().forEachRemaining(reversedCurrentDNAString::add);
        }

        // Add it
        ret.add(new ViterbiResult(reversedCurrentDNAString, start, end, strand, this.input.getName()));
    }
}

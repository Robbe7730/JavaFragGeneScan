package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.*;
import be.robbevanherck.javafraggenescan.transitions.*;
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
        List<AminoAcid> inputs = input.getInputAcids();
        AminoAcid firstInput = inputs.remove(0);
        ViterbiStep currentStep = new ViterbiStep(parameters, firstInput, inputs);
        for (int i = 0; i < input.getInputAcids().size(); i++) {
            // TODO: if there are more than 9 consecutive INVALID's, everything at this step is NON_CODING
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
        Set<ViterbiResult> results = new HashSet<>();
        List<AminoAcid> currentDNAString = new ArrayList<>();

        HMMState currentState = currentStep.getHighestProbabilityState();
        HMMState previousState = HMMState.NO_STATE;

        DNAStrand currentStrand = DNAStrand.UNKNOWN_STRAND;

        int position = sequenceLength - 1;
        int strandEndPosition = -1;

        while (currentState != HMMState.NO_STATE && currentStep.getPrevious() != null) {

            // Check if we are have started a strand
            if (currentStrand == DNAStrand.UNKNOWN_STRAND) {
                currentStrand = tryUpdateDNAStrand(currentState);
                // If it has changed, keep track of when the change happened
                if (currentStrand != DNAStrand.UNKNOWN_STRAND) {
                    strandEndPosition = position;
                }
            }

            // If we have a match state, add it to the current DNA string
            if (currentStrand == DNAStrand.FORWARD && HMMState.isForwardMatchState(currentState)) {
                // Check if we had deletions
                while (HMMState.isForwardMatchState(previousState) && HMMState.nextState(currentState) != previousState) {
                    previousState = HMMState.previousState(previousState);
                    currentDNAString.add(0, AminoAcid.INVALID);
                }

                // Add the value in the front
                currentDNAString.add(0, currentStep.getInput());
            } else if (currentStrand == DNAStrand.REVERSE && HMMState.isReverseMatchState(currentState)) {
                // Check if we had deletions
                while (HMMState.isReverseMatchState(previousState) && HMMState.nextState(currentState) != previousState) {
                    previousState = HMMState.previousState(previousState);
                    currentDNAString.add(AminoAcid.INVALID);
                }

                // Add the value in the back
                currentDNAString.add(DNAUtil.complement(currentStep.getInput()));
            }

            // If we have a start state and are matching, add the result to the set and stop matching
            if (currentStrand != DNAStrand.UNKNOWN_STRAND && (currentState == HMMState.START || currentState == HMMState.START_REVERSE)) {
                // position is + 1 because we are now in a START(_REVERSE) state, so we actually started at the next state
                results.add(new ViterbiResult(currentDNAString, position + 1, strandEndPosition, currentStrand, input.getName()));
                currentStrand = DNAStrand.UNKNOWN_STRAND;
                strandEndPosition = -1;
            }

            currentStep = currentStep.getPrevious();
            previousState = currentState;
            currentState = currentStep.getPathProbabilityFor(currentState).getPreviousState();
            position--;
        }
        // Add remaining strand
        if (currentStrand != DNAStrand.UNKNOWN_STRAND) {
            // Prune incomplete matches
            for (int i = 0; i < matchesToRemove(previousState); i++) {
                position += 1;
                if (currentStrand == DNAStrand.FORWARD) {
                    currentDNAString.remove(0);
                } else {
                    currentDNAString.remove(currentDNAString.size()-1);
                }
            }

            results.add(new ViterbiResult(currentDNAString, position + 1, strandEndPosition, currentStrand, input.getName()));
        }

        return results;
    }

    /**
     * Return the number of left-over matches that need to be removed, given the current state
     * @param previousState The state we ended on
     * @return The number of states that need to be removed to get full codon matches
     */
    private int matchesToRemove(HMMState previousState) {
        if (previousState == HMMState.MATCH_2 || previousState == HMMState.MATCH_5 ||
                previousState == HMMState.MATCH_REVERSE_2 || previousState == HMMState.MATCH_REVERSE_5) {
            return 2;
        } else if (previousState == HMMState.MATCH_3 || previousState == HMMState.MATCH_6 ||
                previousState == HMMState.MATCH_REVERSE_3 || previousState == HMMState.MATCH_REVERSE_6) {
            return 1;
        }
        return 0;
    }

    /**
     * Try to update the DNAStrand depending on the current state
     * @param currentState The current state
     * @return FORWARD if the current state comes from a forward strand, REVERSE if the state comes from
     *         a reverse strand and UNKNOWN_STRAND otherwise
     */
    private DNAStrand tryUpdateDNAStrand(HMMState currentState) {
        if (currentState == HMMState.MATCH_6 || currentState == HMMState.MATCH_3) {
            return  DNAStrand.FORWARD;
        } else if (currentState == HMMState.MATCH_REVERSE_6 || currentState == HMMState.MATCH_REVERSE_3) {
            return DNAStrand.REVERSE;
        } else {
            return DNAStrand.UNKNOWN_STRAND;
        }
    }
}

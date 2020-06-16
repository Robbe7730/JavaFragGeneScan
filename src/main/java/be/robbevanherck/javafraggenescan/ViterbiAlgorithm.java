package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMParameters;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.ViterbiStep;
import be.robbevanherck.javafraggenescan.transitions.*;

import java.util.List;

/**
 * Main class for everything related to the Viterbi algorithm
 */
public class ViterbiAlgorithm {
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
     * @param parameters The parameters for the HMM
     */
    public ViterbiAlgorithm(HMMParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Run the entire algorithm, clears the state when completed
     * @param input The input for the algorithm
     * @return The last step, which can be used for backtracking
     */
    public ViterbiStep run(List<AminoAcid> input) {

        ViterbiStep currentStep = new ViterbiStep(parameters, input);
        for (int i = 1; i < input.size() - 1; i++) {
            currentStep = currentStep.calculateNext(input.get(i), input.get(i+1));
        }
        currentStep = currentStep.calculateNext(input.get(input.size() - 1), null);
        return currentStep;
    }
}

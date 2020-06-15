package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.transitions.*;

import java.util.List;

/**
 * Repository containing the instances of state transitions
 */
public class TransitionRepository {
    private TransitionRepository(){}

    private static TransitionRepository instance;

    private static final List<Transition> transitions = List.of(
            // M state
            new MatchForwardFirstTransition(),                          // M1
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
            new InsertForwardSixthTransition(),                         // I6

            // M' state
            new MatchForwardTransition(HMMState.MATCH_REVERSE_1),      // M1'
            new MatchForwardTransition(HMMState.MATCH_REVERSE_2),      // M2'
            new MatchForwardTransition(HMMState.MATCH_REVERSE_3),      // M3'
            new MatchForwardTransition(HMMState.MATCH_REVERSE_4),      // M4'
            new MatchForwardTransition(HMMState.MATCH_REVERSE_5),      // M5'
            new MatchReverseSixthTransition(),                          // M6'

            // I' state
            new InsertReverseTransition(HMMState.INSERT_REVERSE_1),    // I1'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_2),    // I2'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_3),    // I3'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_4),    // I4'
            new InsertReverseTransition(HMMState.INSERT_REVERSE_5),    // I5'
            new InsertReverseSixthTransition(),                         // I6'

            // R state (non-coding)
            new NonCodingTransition(),                                  // R

            // End state
            new EndForwardTransition(),                                 // E

            // Start' state
            new StartReverseTransition(),                               // S'

            // Start state
            new StartForwardTransition(),                               // S

            // End' state
            new EndReverseTransition()                                  // E'
    );

    public static TransitionRepository getInstance() {
        if (instance == null) {
            instance = new TransitionRepository();
        }
        return instance;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
}

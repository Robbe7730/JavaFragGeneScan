package be.robbevanherck.javafraggenescan;

import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;

import java.util.List;

public class TestUtil {
    // STATES

    public static final List<HMMState> matchingStates = List.of(
            HMMState.MATCH_1,
            HMMState.MATCH_2,
            HMMState.MATCH_3,
            HMMState.MATCH_4,
            HMMState.MATCH_5,
            HMMState.MATCH_6
    );
    public static final List<HMMState> matchingReverseStates = List.of(
            HMMState.MATCH_REVERSE_1,
            HMMState.MATCH_REVERSE_2,
            HMMState.MATCH_REVERSE_3,
            HMMState.MATCH_REVERSE_4,
            HMMState.MATCH_REVERSE_5,
            HMMState.MATCH_REVERSE_6
    );
    public static final List<HMMState> insertStates = List.of(
            HMMState.INSERT_1,
            HMMState.INSERT_2,
            HMMState.INSERT_3,
            HMMState.INSERT_4,
            HMMState.INSERT_5,
            HMMState.INSERT_6
    );
    public static final List<HMMState> insertReverseStates = List.of(
            HMMState.INSERT_REVERSE_1,
            HMMState.INSERT_REVERSE_2,
            HMMState.INSERT_REVERSE_3,
            HMMState.INSERT_REVERSE_4,
            HMMState.INSERT_REVERSE_5,
            HMMState.INSERT_REVERSE_6
    );
    public static final List<HMMState> startStopStates = List.of(
            HMMState.START,
            HMMState.START_REVERSE,
            HMMState.END,
            HMMState.END_REVERSE
    );

    // AMINO ACIDS

    public static final List<AminoAcid> aminoAcids = List.of(
            AminoAcid.A,
            AminoAcid.C,
            AminoAcid.G,
            AminoAcid.T
    );

    // START/STOP CODONS

    public static final List<Triple<AminoAcid>> forwardStartCodons = List.of(
            new Triple<>(AminoAcid.A, AminoAcid.T, AminoAcid.G),
            new Triple<>(AminoAcid.G, AminoAcid.T, AminoAcid.G),
            new Triple<>(AminoAcid.T, AminoAcid.T, AminoAcid.G)
    );

    public static final List<Triple<AminoAcid>> reverseStartCodons = List.of(
            new Triple<>(AminoAcid.T, AminoAcid.T, AminoAcid.A),
            new Triple<>(AminoAcid.T, AminoAcid.C, AminoAcid.A),
            new Triple<>(AminoAcid.C, AminoAcid.T, AminoAcid.A)
    );

    public static final List<Triple<AminoAcid>> forwardStopCodons = List.of(
            new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.A),
            new Triple<>(AminoAcid.T, AminoAcid.A, AminoAcid.G),
            new Triple<>(AminoAcid.T, AminoAcid.G, AminoAcid.A)
    );

    public static final List<Triple<AminoAcid>> reverseStopCodons = List.of(
            new Triple<>(AminoAcid.C, AminoAcid.A, AminoAcid.T),
            new Triple<>(AminoAcid.C, AminoAcid.A, AminoAcid.C),
            new Triple<>(AminoAcid.C, AminoAcid.A, AminoAcid.A)
    );

}

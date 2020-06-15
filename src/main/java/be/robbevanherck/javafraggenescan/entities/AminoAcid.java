package be.robbevanherck.javafraggenescan.entities;

/**
 * Represents the amino-acids
 */
public enum AminoAcid {
    A,
    C,
    G,
    T,
    INVALID;

    /**
     * Return the amino-acid at position i in alphabetical order
     * @param i The position (0-3)
     * @return The amino-acid
     */
    public static AminoAcid fromInt(int i) {
        switch (i) {
            case 0:
                return A;
            case 1:
                return C;
            case 2:
                return G;
            case 3:
                return T;
            default:
                return INVALID;
        }
    }
}

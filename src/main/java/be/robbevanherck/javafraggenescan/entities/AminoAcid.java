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

    /**
     * Turn the AminoAcid into an int
     * @param acid The acid
     * @return The number
     */
    public static int toInt(AminoAcid acid) {
        switch (acid) {
            case A:
                return 0;
            case C:
                return 1;
            case G:
                return 2;
            case T:
                return 3;
            default:
                return -1;
        }
    }
    /**
     * Create an AminoAcid from a single-letter string
     * @param letter Either A, C, G or T
     * @return The corresponding AminoAcid or INVALID if the string doesn't match
     */
    public static AminoAcid fromString(String letter) {
        switch (letter) {
            case "A":
                return A;
            case "C":
                return C;
            case "G":
                return G;
            case "T":
                return T;
            default:
                return INVALID;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case A:
                return "A";
            case C:
                return "C";
            case G:
                return "G";
            case T:
                return "T";
            default:
                return "N";
        }
    }
}

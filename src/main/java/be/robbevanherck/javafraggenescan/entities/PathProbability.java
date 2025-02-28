package be.robbevanherck.javafraggenescan.entities;

/**
 * Combination of previous state and probability
 */
public class PathProbability {
    private double probability;
    private final HMMState previousState;

    /**
     * Create a new PathProbability
     * @param previousState The previous state
     * @param probability The probability
     */
    public PathProbability(HMMState previousState, double probability) {
        this.probability = probability;
        this.previousState = previousState;
    }

    /**
     * Return the PathProbability with the highest probability
     * @param firstPP The first PathProbability
     * @param secondPP The second PathProbability
     * @return The biggest PP
     */
    public static PathProbability max(PathProbability firstPP, PathProbability secondPP) {
        if (firstPP.getProbability() > secondPP.getProbability()) {
            return firstPP;
        }
        return secondPP;
    }
    /**
     * Return the PathProbability with the highest probability
     * @param firstPP The first PathProbability
     * @param secondPP The second PathProbability
     * @param thirdPP The third PathProbability
     * @return The biggest PP
     */
    public static PathProbability max(PathProbability firstPP, PathProbability secondPP, PathProbability thirdPP) {
        return PathProbability.max(PathProbability.max(firstPP, secondPP), thirdPP);
    }

    public double getProbability() {
        return probability;
    }

    public HMMState getPreviousState() {
        return previousState;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String toString() {
        return probability + " -> " + previousState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathProbability that = (PathProbability) o;

        if (Double.compare(that.probability, probability) != 0) return false;
        return previousState == that.previousState;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(probability);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + previousState.hashCode();
        return result;
    }
}

package be.robbevanherck.javafraggenescan.entities;

/**
 * Represents a pair
 * @param <T> The type of the elements
 */
public class Pair<T> {
    private final T firstValue;
    private final T secondValue;

    /**
     * Create a pair
     * @param firstValue The first value
     * @param secondValue The second value
     */
    public Pair(T firstValue, T secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public T getFirstValue() {
        return firstValue;
    }

    public T getSecondValue() {
        return secondValue;
    }

    /**
     * Append a value to the pair to create a triple
     * @param newValue The value to be added to the end
     * @return The new Triple
     */
    public Triple<T> append(T newValue) {
        return new Triple<>(
                firstValue,
                secondValue,
                newValue
        );
    }
}

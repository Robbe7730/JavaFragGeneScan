package be.robbevanherck.javafraggenescan.entities;

/**
 * Represents a triple
 * @param <T> The type of the elements
 */
public class Triple<T> {
    private final T firstValue;
    private final T secondValue;
    private final T thirdValue;

    /**
     * Create a triple
     * @param firstValue The first value
     * @param secondValue The second value
     * @param thirdValue The third value
     */
    public Triple(T firstValue, T secondValue, T thirdValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    public T getFirstValue() {
        return firstValue;
    }

    public T getSecondValue() {
        return secondValue;
    }

    public T getThirdValue() {
        return thirdValue;
    }
}

package be.robbevanherck.javafraggenescan.entities;

import java.util.Objects;

/**
 * Represents a triple
 * @param <T> The type of the elements
 */
public class Triple<T> {
    private T firstValue;
    private T secondValue;
    private T thirdValue;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple<?> triple = (Triple<?>) o;

        if (!Objects.equals(firstValue, triple.firstValue)) return false;
        if (!Objects.equals(secondValue, triple.secondValue)) return false;
        return Objects.equals(thirdValue, triple.thirdValue);
    }

    @Override
    public int hashCode() {
        int result = firstValue != null ? firstValue.hashCode() : 0;
        result = 31 * result + (secondValue != null ? secondValue.hashCode() : 0);
        result = 31 * result + (thirdValue != null ? thirdValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Triple(" + firstValue + "," + secondValue + "," + thirdValue + ')';
    }

    /**
     * Check if the triple contains a given value
     * @param value The value to check
     * @return True if it contains the value, false otherwise
     */
    public boolean contains(T value) {
        return firstValue == value || secondValue == value || thirdValue == value;
    }

    /**
     * Replace all occurrences of from to to
     * @param from The value to replace
     * @param to The value to replace it with
     */
    public void replaceAll(T from, T to) {
        if (firstValue == from) {
            firstValue = to;
        }
        if (secondValue == from) {
            secondValue = to;
        }
        if (thirdValue == from) {
            thirdValue = to;
        }
    }
}

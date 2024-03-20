package tech.team28.heslingtonhustle;

/**
 * The {@code Counter} class represents a simple counter that can be incremented and whose value can
 * be retrieved.
 */
public class Counter {

    private int count = 0;

    /** Constructs a new {@code Counter} with an initial count of 0. */
    public Counter() {}

    /** Increments the count by 1. */
    public void incrementCount() {
        count += 1;
    }

    /**
     * Returns the current count value.
     *
     * @return The current count value.
     */
    public int getCount() {
        return count;
    }
}

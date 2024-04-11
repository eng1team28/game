package tech.team28.heslingtonhustle;

/**
 * Data holder for counting how many times the player has interacted with different kinds of
 * interactable.
 */
public class AreaCounter {
    // Fields for counting each kind of interaction event.
    private final Counter eatAreaCounter = new Counter();
    private final Counter recreationAreaCounter = new Counter();
    private final Counter sleepAreaCounter = new Counter();
    private final Counter studyAreaCounter = new Counter();

    /** Default empty constructor for the AreaCounter class. */
    public AreaCounter() {}

    /**
     * Gets the counter for eat areas.
     *
     * @return The counter for eat areas.
     */
    public Counter getEatAreaCounter() {
        return eatAreaCounter;
    }

    /**
     * Gets the counter for recreational areas.
     *
     * @return The counter for recreational areas.
     */
    public Counter getRecreationAreaCounter() {
        return recreationAreaCounter;
    }

    /**
     * Gets the counter for sleep areas.
     *
     * @return The counter for sleep areas.
     */
    public Counter getSleepAreaCounter() {
        return sleepAreaCounter;
    }

    /**
     * Gets the counter for study areas.
     *
     * @return The counter for study areas.
     */
    public Counter getStudyAreaCounter() {
        return studyAreaCounter;
    }
}

package tech.team28.heslingtonhustle;

/**
 * Data holder for counting how many times the player has interacted with different kinds of
 * interactable.
 */
public class AreaCounter {
    // Fields for counting each kind of interaction event.
    private int eatAreaCounter = 0;
    private int recreationAreaCounter = 0;
    private int sleepAreaCounter = 0;
    private int studyAreaCounter = 0;

    /** Default empty constructor for the AreaCounter class. */
    public AreaCounter() {}

    /**
     * Gets the counter for eat areas.
     *
     * @return The counter for eat areas.
     */
    public int getEatAreaCounter() {
        return eatAreaCounter;
    }

    /** Increments the counter for eat areas. */
    public void incrementEatAreaCounter() {
        eatAreaCounter++;
    }

    /**
     * Gets the counter for recreational areas.
     *
     * @return The counter for recreational areas.
     */
    public int getRecreationAreaCounter() {
        return recreationAreaCounter;
    }

    /** Increments the counter for recreational areas. */
    public void incrementRecreationAreaCounter() {
        recreationAreaCounter++;
    }

    /**
     * Gets the counter for sleep areas.
     *
     * @return The counter for sleep areas.
     */
    public int getSleepAreaCounter() {
        return sleepAreaCounter;
    }

    /** Increments the counter for sleep areas. */
    public void incrementSleepAreaCounter() {
        sleepAreaCounter++;
    }

    /**
     * Gets the counter for study areas.
     *
     * @return The counter for study areas.
     */
    public int getStudyAreaCounter() {
        return studyAreaCounter;
    }

    /** Increments the counter for study areas. */
    public void incrementStudyAreaCounter() {
        studyAreaCounter++;
    }
}

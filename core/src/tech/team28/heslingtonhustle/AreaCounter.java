package tech.team28.heslingtonhustle;

/**
 * Represents a counter for different types of areas in the game.
 */
public class AreaCounter {

    /**
    * Represents a counter for different types of areas in the game.
    */
    private int eatAreaCounter = 0;
    private int RecreationalAreaCounter = 0;
    private int SleepAreaCounter = 0;
    private int StudyAreaCounter = 0;

    /**
     * Default constructor for the AreaCounter class.
     */
    public AreaCounter() {}

    /**
     * Gets the counter for eat areas.
     * @return The counter for eat areas.
     */
    public int getEatAreaCounter() {
        return eatAreaCounter;
    }

    /**
     * Sets the counter for eat areas.
     * @param eatAreaCounter parameter used for eat areas to set.
     */
    public void setEatAreaCounter(int eatAreaCounter) {
        this.eatAreaCounter = eatAreaCounter;
    }

    /**
     * Gets the counter for recreational areas.
     * @return The counter for recreational areas.
     */
    public int getRecreationalAreaCounter() {
        return RecreationalAreaCounter;
    }

    /**
     * Sets the counter for recreational areas.
     * @param recreationalAreaCounter The parameter used for recreational areas to set.
     */
    public void setRecreationalAreaCounter(int recreationalAreaCounter) {
        RecreationalAreaCounter = recreationalAreaCounter;
    }

    /**
     * Gets the counter for sleep areas.
     * @return The counter for sleep areas.
     */
    public int getSleepAreaCounter() {
        return SleepAreaCounter;
    }

    /**
     * Sets the counter for sleep areas.
     * @param sleepAreaCounter The parameter used for sleep areas to set.
     */
    public void setSleepAreaCounter(int sleepAreaCounter) {
        SleepAreaCounter = sleepAreaCounter;
    }

    /**
     * Gets the counter for study areas.
     * @return The counter for study areas.
     */
    public int getStudyAreaCounter() {
        return StudyAreaCounter;
    }

    /**
     * Sets the counter for study areas.
     * @param studyAreaCounter The parameter used for study areas to set.
     */
    public void setStudyAreaCounter(int studyAreaCounter) {
        StudyAreaCounter = studyAreaCounter;
    }
}

package tech.team28.heslingtonhustle;

import com.badlogic.gdx.utils.Array;

/**
 * Singleton class for holding and managing the overarching state of the game. This includes play
 * region width and height, day of the week, and time of day.
 */
public class GameManager {
    /**
     * Width of the game region in arbitrary units. This is not the size of the window in pixels,
     * the game region is scaled by the camera.
     */
    public static final float GAME_WIDTH = 2048f;

    /** Height of the game region in arbitrary units. */
    public static final float GAME_HEIGHT = 2048f;

    /**
     * Height to apply to the viewport in the same arbitrary units. View width is dynamically
     * determined by window aspect ratio.
     */
    public static final float VIEW_HEIGHT = 512f;

    /** Basic enum for days of the week. */
    enum Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
    }

    /** Current day respective to the game. */
    private Day currentDay;

    /** Current time in hours. */
    private float time;

    /** Maximum duration of a day in the game, in hours. */
    private static final float DAY_DURATION = 24;

    /** Hour at which to start a new day after the player sleeps. */
    private static final float DAY_START_TIME = 8;

    /** Counters for different areas in the game */
    private final AreaCounter areaCounter = new AreaCounter();

    /** Array of interactable objects in the game */
    private final Array<Interactable> interactables;

    /** Reference to main game instance. */
    private HeslingtonHustle game;

    /** The player. */
    private Player player;

    /** Singleton instance of the GameManager */
    private static GameManager instance;

    /**
     * Private constructor to prevent instantiation from outside the class. Initializes default
     * values for weekday and time, and creates an empty array for interactables.
     */
    private GameManager() {
        currentDay = Day.MONDAY;
        time = GameManager.DAY_START_TIME;
        interactables = new Array<>(4);
    }

    /**
     * Retrieves the singleton instance of the GameManager. If the instance does not exist, creates
     * a new one.
     *
     * @return The GameManager instance.
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Determines the player
     *
     * @param player The player that's playing
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /** Retrieves the list of interactable objects and returns them in an array */
    public Array<Interactable> getInteractables() {
        return interactables;
    }

    /**
     * Adds a new interactable object to the game.
     *
     * @param newInteractable The new interactable object to add.
     */
    public void addInteractable(Interactable newInteractable) {
        // Todo manage interactable array size thoroughly>
        interactables.add(newInteractable);
    }

    /**
     * Set the game attribute.
     *
     * @param currentGame The main game instance
     */
    public void setGame(HeslingtonHustle currentGame) {
        this.game = currentGame;
    }

    /** Reset the time of day to when you wake up. */
    public void resetTime() {
        time = GameManager.DAY_START_TIME;
    }

    /**
     * Try to increment the time in the game by the specified amount.
     *
     * <p>This will succeed if there's enough time left in the day, and fail if adding the amount
     * would exceed the day duration.
     *
     * @param amount The amount by which to increment the time.
     * @return True if the time was successfully incremented, False otherwise.
     */
    public boolean incrementTime(float amount) {
        float newTime = time + amount;
        if (newTime < 0 || newTime >= DAY_DURATION) {
            return false;
        } else {
            time = newTime;
            return true;
        }
    }

    /**
     * Formats time with two digits, zero-padded, no decimal point. I.E. as 24hr format.
     *
     * @return The formatted String.
     */
    String getTimeFormatted() {
        //
        return String.format("Time: %02.0f:00", getTime());
    }

    /**
     * Getter for the current time.
     *
     * @return The current time of day in hours.
     */
    public float getTime() {
        return time;
    }

    /**
     * Getter for the current day of the week.
     *
     * @return the enum value.
     */
    Day getCurrentDay() {
        return currentDay;
    }

    /**
     * Getter for the counter instance.
     *
     * @return the interact counter.
     */
    public AreaCounter getAreaCounter() {
        return areaCounter;
    }

    /**
     * Get the day as a formatted string.
     *
     * @return the current day of the week in-game, in title case.
     */
    String getDayFormatted() {
        String dayCapitalised = Util.capitaliseString(getCurrentDay().name());
        return String.format("Day: %s", dayCapitalised);
    }

    /**
     * Try to progress to the next day of the week.
     *
     * <p>If the current day is Sunday, it won't increment, it will call the method to take the exam
     * instead.
     *
     * @return true if the day was incremented, false otherwise.
     */
    public boolean incrementDay() {
        if (currentDay == Day.SUNDAY) {
            takeExam();
            return false;
        } else {
            Day[] days = Day.values(); // get an array of all the enum constants
            int index = currentDay.ordinal(); // get the index of the current day in the array
            index++; // increment the index
            currentDay = days[index]; // get the new enum value and assign it to the day variable
            return true;
        }
    }

    /**
     * Set the current day to the final day and then call the increment method, for testing the end
     * of the game.
     */
    public void setEndDay() {
        this.currentDay = Day.values()[6];
        this.incrementDay();
    }

    /**
     * Determine whether the player passed the exam based on how many times they studied, then tell
     * the game to play the end cutscene with the appropriate result.
     */
    private void takeExam() {
        boolean examWin;
        examWin = areaCounter.getStudyAreaCounter().getCount() >= 8;
        player.setPosition(950, 500); // Move player into a position, so they can see the result
        this.game.examCutscene(examWin);
    }

    /**
     * Get all the area counters as a single string with abbreviated names and newlines.
     *
     * @return the formatted String.
     */
    public String getCountersFormatted() {
        return String.format(
                "EAT: %d\nREC: %d\nSLP: %d\nSTD: %d",
                areaCounter.getEatAreaCounter().getCount(),
                areaCounter.getRecreationAreaCounter().getCount(),
                areaCounter.getSleepAreaCounter().getCount(),
                areaCounter.getStudyAreaCounter().getCount());
    }
}

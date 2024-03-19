package tech.team28.heslingtonhustle;

import com.badlogic.gdx.utils.Array;

public class GameManager {
    // Size of the game region in arbitrary units
    // This is not the size of the window in pixels
    // The game region is scaled by the camera
    public static final float GAME_WIDTH = 2048f;
    public static final float GAME_HEIGHT = 2048f;
    // View width is dynamically determined by window aspect ratio
    public static final float VIEW_HEIGHT = 512f;

    enum Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
    }

    private Day currentDay; // Current day respective to the game
    private float time; // Current time
    private final static float DAY_DURATION = 24; // Duration of a day in the game
    private final static float DAY_START_TIME = 8;
    // Counters for different areas in the game
    private final AreaCounter areaCounter = new AreaCounter();
    private Array<Interactable> interactables; // Array of interactable objects in the game
    private HeslingtonHustle game;
    private Player player; // The player

    /**
     * Private constructor to prevent instantiation from outside the class. Initializes default
     * values for day, time, and interactables.
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

    // Retrieves the list of interactable objects and returns them in an array
    public Array<Interactable> getInteractables() {
        return interactables;
    }

    /**
     * Adds a new interactable object to the game.
     *
     * @param newInteractable The new interactable object to add.
     */
    public void addInteractable(Interactable newInteractable) {
        interactables.add(newInteractable);
    }

    // Singleton instance of the GameManager
    private static GameManager instance;

    public void setGame(HeslingtonHustle currentGame) {
        this.game = currentGame;
    }

    public void resetTime() {
        time = GameManager.DAY_START_TIME;
    }

    /**
     * Increments the time in the game by the specified amount. If the time exceeds a day's
     * duration, increments the day.
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

    String getTimeFormatted() {
        // Formats time with two digits, zero-padded, no decimal point
        // I.E. as 24hr format
        return String.format("Time: %02.0f:00", getTime());
    }

    /**
     * Getter for the current time
     *
     * @return The current time of day in arbitrary units.
     */
    public float getTime() {
        return time;
    }

    Day getCurrentDay() {
        return currentDay;
    }

    public AreaCounter getAreaCounter() {
        return areaCounter;
    }

    String getDayFormatted() {
        return String.format("Day: %s", getCurrentDay());
    }

    boolean incrementDay() {
        if (currentDay == Day.SUNDAY) {
            return false;
        } else {
            Day[] days = Day.values(); // get an array of all the enum constants
            int index = currentDay.ordinal(); // get the index of the current day in the array
            index++; // increment the index
            currentDay = days[index]; // get the new enum value and assign it to the day variable
            return true;
        }
    }

    public void setEndDay() {
        this.currentDay = Day.values()[6];
        this.incrementDay();
    }

    private void takeExam() {
        boolean examWin;
        examWin = player.getIntelligence() >= 60;
        player.setPosition(950, 500); // Move player into a position, so they can see the result
        this.game.examCutscene(examWin);
    }
}

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

    HeslingtonHustle game;

    enum Day {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday,
        ExamDay
    }

    private Day day; // Current day respective to the game
    private float time; // Current time
    private final static float dayDuration = 24; // Duration of a day in the game
    private Player player; // The player
    // Counter for different areas in the game
    private final AreaCounter areaCounter = new AreaCounter();
    private Array<Interactable> interactables; // Array of interactable objects in the game

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

    /**
     * Private constructor to prevent instantiation from outside the class. Initializes default
     * values for day, time, and interactables.
     */
    private GameManager() {
        day = Day.Monday;
        time = 7;
        interactables = new Array<>(4);
    }

    public void SetGame(HeslingtonHustle currentGame) {
        this.game = currentGame;
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
     * Increments the time in the game by the specified amount. If the time exceeds a day's
     * duration, increments the day.
     *
     * @param amount The amount by which to increment the time.
     * @return True if the time was successfully incremented, False otherwise.
     */
    public boolean incrementTime(float amount) {
        float newTime = time + amount;
        if (newTime >= dayDuration) {
            incrementDay();
            incrementTime(newTime - 24);
            if (day == Day.Sunday && newTime > dayDuration) {
                day = Day.Monday;
            }
        } else {
            time = newTime;
        }
        return true;
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

    Day getDay() {
        return day;
    }

    public AreaCounter getAreaCounter() {
        return areaCounter;
    }

    String getDayFormatted() {
        return String.format("Day: %s", getDay());
    }

    Day incrementDay() {
        time = 0; // reset time of day
        Day[] days = Day.values(); // get an array of all the enum constants
        if (this.day.equals(days[6])) {
            this.TakeExam();
            return days[7];
        } else {

            int index = day.ordinal(); // get the index of the current day in the array
            index = (index + 1) % days.length; // add one to the index and wrap around the array
            day = days[index]; // get the new enum value and assign it to the day variable
            return day;
        }
    }

    public void setEndDay() {
        this.day = Day.values()[6];
        this.incrementDay();
    }

    private void TakeExam() {

        boolean examWin;
        examWin = player.getIntelligence() >= 60;
        player.setPosition(950, 500); // Move player into a position, so they can see the result
        this.game.examCutscene(examWin);
    }
}

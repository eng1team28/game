package tech.team28.heslingtonhustle;

import com.badlogic.gdx.utils.Array;

public class GameManager {
    // Size of the game region in arbitrary units
    // This is not the size of the window in pixels
    // The game region is scaled by the camera
    public static final float GAME_WIDTH = 2568;
    public static final float GAME_HEIGHT = 1424f;
    // View width is dynamically determined by window aspect ratio
    public static final float VIEW_HEIGHT = 712f;

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

    private Day day;
    private float time;
    private final float dayDuration;
    private Player player;
    private final AreaCounter areaCounter = new AreaCounter();
    private Array<Interactable> interactables;

    /**
     * Setter for the player
     *
     * @return The player entity.
     */

    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * Getter for the Interactables
     *
     * @return The array of interactables.
     */

    public Array<Interactable> getInteractables() {
        return interactables;
    }

    /**
     * Adds an interactable to the array of interactables.
     *
     * @return True if the interactable was added successfully.
     */

    public boolean addInteractable(Interactable newInteractable) {
        interactables.add(newInteractable);
        return true;
    }

    private static GameManager instance;

    /**
     * Constructor for the GameManager class. Initializes the day, time, and interactables.
     *
     *
     */

    private GameManager() {
        day = Day.Monday;
        time = 0;
        dayDuration = 24;
        interactables = new Array<Interactable>(4);
    }


    public void SetGame(HeslingtonHustle currentGame){
        this.game = currentGame;
    }


    /**
     * Returns the instance of the GameManager class. If the instance does not exist, it is created.
     *
     * @return The instance of the GameManager class.
     */

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Increments the time by the given amount. If the time exceeds the day duration, the day is
     * incremented and the time is reset to 0. If the day is Sunday, the player takes an exam.
     *
     * @param amount The amount of time to increment by.
     */

    public boolean incrementTime(float amount) {
        float newTime = time + amount;
        if (newTime >= dayDuration) {
            incrementDay();
            if (day == Day.Sunday && newTime > dayDuration) {
                TakeExam();
                day = Day.Monday;
            }
        } else {
            time = newTime;
        }
        return true;
    }

    /**
     * Formats the time with two digits, zero-padded, no decimal point.
     * I.E. as 24hr format
     *
     *
     * @return The formatted time.
     */

    String getTimeFormatted() {
        // Formats time with two digits, zero-padded, no decimal point
        // I.E. as 24hr format
        return String.format("Time: %02.0f:00", getTime());
    }

    /**
     * Getter for the current time
     *
     * @return The current time of day in arbitrary units.
     *
     */

    float getTime() {
        return time;
    }


    /**
     * Getter for the current day
     *
     * @return The current day of the week.
     */
    Day getDay() {
        return day;
    }

    public AreaCounter getAreaCounter() {
        return areaCounter;
    }

    String getDayFormatted() {
        return String.format("Day: %s", getDay());
    }


    /**
     * Increments the day by one. If the day is Sunday, the day is reset to Monday.
     *
     * @return The new day of the week.
     */
    Day incrementDay() {
        if (this.day == Day.Sunday){
            TakeExam();
            day = Day.values()[7];//This should always return ExamDay. Also this might be a terrible way of doing this
            return day;
        }else{
            time = 0; // reset time of day

            Day[] days = Day.values(); // get an array of all the enum constants
            int index = day.ordinal(); // get the index of the current day in the array
            index = (index + 1) % days.length; // add one to the index and wrap around the array
            day = days[index]; // get the new enum value and assign it to the day variable
            return day;
        }
    }

    public void setEndDay(){
        this.day = Day.values()[6];
        this.incrementDay();
    }

    private void TakeExam() {

        boolean examWin;
        examWin = player.getIntelligence() >= 60;
        player.setPosition(0, 0);//Move player into a position so they can see the result
        this.game.examCutscene(examWin);
        
    }
}

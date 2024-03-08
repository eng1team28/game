package tech.team28.heslingtonhustle;

public class GameManager {
    static final int SCREEN_WIDTH = 1920;
    static final int SCREEN_HEIGHT = 1080;

    private static GameManager instance;

    enum Day {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday;
    }

    private Day day;
    private float time;
    private final float dayDuration;

    private GameManager() {
        day = Day.Monday;
        time = 0;
        dayDuration = 24;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    boolean incrementTime(float amount) {
        float newTime = time + amount;
        if (newTime >= dayDuration) {
            return false;
        } else {
            time = newTime;
            return true;
        }
    }

    String getTime() {
        return "Time:" + time + ":00";
    }

    Day incrementDay() {
        // TODO - What happens after Sunday? does index need to wrap around array?
        time = 0; // reset time of day

        if (day == Day.Sunday) {
            TakeExam();
            return null;
        }
        Day[] days = Day.values(); // get an array of all the enum constants
        int index = day.ordinal(); // get the index of the current day in the array
        index = (index + 1) % days.length; // add one to the index and wrap around the array
        day = days[index]; // get the new enum value and assign it to the day variable
        return day;
    }

    private void TakeExam() {
        // TODO - Exam Logic
        return;
    }
}

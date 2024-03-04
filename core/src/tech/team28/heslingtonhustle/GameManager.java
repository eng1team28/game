package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Game;

public class GameManager {

    private static GameManager instance;

    enum Day{
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



    private GameManager(){
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

    float incrementTime(float amount){
        time += amount;
        if (time >= dayDuration){
            time = 0;
            incrementDay();
        }
        return time;
    }

    Day incrementDay(){
        // TODO - What happens after Sunday? does index need to wrap around array?
        if(day == Day.Sunday){
            TakeExam();
            return null;
        }
        Day[] days = Day.values(); // get an array of all the enum constants
        int index = day.ordinal(); // get the index of the current day in the array
        index = (index + 1) % days.length; // add one to the index and wrap around the array
        day = days[index]; // get the new enum value and assign it to the day variable
        return day;
    }

    private void TakeExam(){
        // TODO - Exam Logic
        return;
    }


}

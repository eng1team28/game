package tech.team28.heslingtonhustle;

public class AreaCounter {

    private int eatAreaCounter = 0;
    private int RecreationalAreaCounter = 0;
    private int SleepAreaCounter = 0;
    private int StudyAreaCounter = 0;

    public AreaCounter() {}

    public int getEatAreaCounter() {
        return eatAreaCounter;
    }

    public void setEatAreaCounter(int eatAreaCounter) {
        this.eatAreaCounter = eatAreaCounter;
    }

    public int getRecreationalAreaCounter() {
        return RecreationalAreaCounter;
    }

    public void setRecreationalAreaCounter(int recreationalAreaCounter) {
        RecreationalAreaCounter = recreationalAreaCounter;
    }

    public int getSleepAreaCounter() {
        return SleepAreaCounter;
    }

    public void setSleepAreaCounter(int sleepAreaCounter) {
        SleepAreaCounter = sleepAreaCounter;
    }

    public int getStudyAreaCounter() {
        return StudyAreaCounter;
    }

    public void setStudyAreaCounter(int studyAreaCounter) {
        StudyAreaCounter = studyAreaCounter;
    }
}

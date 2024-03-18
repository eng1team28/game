package tech.team28.heslingtonhustle;

/**
 * Manages exams in the game, determining whether the player passes or fails based on their intelligence.
 */
public class ExamManager {

    private final Player player;
    //A set pass mark
    private float passMark = 60;
    /**
     * Constructs an ExamManager with the specified player and pass mark.
     * @param player The player taking the exam.
     * @param passMark The pass mark required to pass the exam.
     */
    public ExamManager(Player player, float passMark) {
        this.player = player;
        this.passMark = passMark;
    }

    /**
     * Initiatalises the exam-taking process.
     * Determines whether the player passes or fails the exam based on their intelligence.
     */
    public void TakeExam() {
        if (player.getIntelligence() < passMark) {
            FailExam();
            return;
        }
        //Runs the pass exam scenario
        PassExam();
    }
    //The pass exam scenario
    private void PassExam() {}
    //The fail exam scenario
    private void FailExam() {}
}

package tech.team28.heslingtonhustle;

public class ExamManager {

    private final Player player;
    private float passMark = 60;

    public ExamManager(Player player, float passMark) {
        this.player = player;
        this.passMark = passMark;
    }

    public void TakeExam() {
        if (player.getIntelligence() < passMark) {
            FailExam();
            return;
        }

        PassExam();
    }

    private void PassExam() {}

    private void FailExam() {}
}

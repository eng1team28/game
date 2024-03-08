package tech.team28.heslingtonhustle;

public class StudyArea implements Interactable {

    private final float studyDuration;
    private final float studyEnergyCost;

    public StudyArea(float studyDuration, float studyEnergyCost) {
        this.studyDuration = studyDuration;
        this.studyEnergyCost = studyEnergyCost;
    }

    public StudyArea() {
        this(1, 1);
    } // Overriding Constructor for default values

    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(studyDuration);

        applyStudyEffect(player);
    }

    private void applyStudyEffect(Player player) {

        player.setEnergy(player.getEnergy() - studyEnergyCost);
        player.setIntelligence(player.getIntelligence() + 10); // Increase intelligence by 10, this can be altered to more/less

    }
}

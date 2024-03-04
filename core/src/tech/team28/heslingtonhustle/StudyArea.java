package tech.team28.heslingtonhustle;

public class StudyArea implements Interactable{

    private final float studyDuration;
    private final float studyEnergyCost;

    public StudyArea(float studyDuration, float studyEnergyCost){
        this.studyDuration = studyDuration;
        this.studyEnergyCost = studyEnergyCost;
    }
    public StudyArea() { this(1, 1); } // Overriding Constructor for default values

    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(studyDuration);
    }

    private void applyStudyEffect(Player player){
        player.setEnergy(player.getEnergy() - studyEnergyCost);
    }
}

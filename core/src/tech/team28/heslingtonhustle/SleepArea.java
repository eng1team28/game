package tech.team28.heslingtonhustle;

public class SleepArea implements Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;

    public SleepArea(float sleepDuration, float sleepEnergyRecovery) {
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;
    }

    @Override
    public void Interact(Player player) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.incrementDay();
        gameManager.incrementTime(8);

        applySleepEffect(player);
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
    }
}

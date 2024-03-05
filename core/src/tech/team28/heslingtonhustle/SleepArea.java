package tech.team28.heslingtonhustle;

public class SleepArea implements Interactable {
    @Override
    public void Interact(Player player) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.incrementDay();
        gameManager.incrementTime(8);
    }
}

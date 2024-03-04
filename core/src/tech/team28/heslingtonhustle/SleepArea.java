package tech.team28.heslingtonhustle;

public class SleepArea implements Interactable {
    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(1);
    }
}

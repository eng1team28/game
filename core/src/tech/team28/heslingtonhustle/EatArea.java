package tech.team28.heslingtonhustle;

public class EatArea implements Interactable {
    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(1);

        applyEatEffect(player);
    }

    private void applyEatEffect(Player player) {
        return;
    }
}

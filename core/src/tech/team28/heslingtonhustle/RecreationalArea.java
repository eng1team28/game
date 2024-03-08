package tech.team28.heslingtonhustle;

public class RecreationalArea implements Interactable {
    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(1);

        applyRecreationalEffect(player);
    }

    private void applyRecreationalEffect(Player player) {
        return;
    }
}
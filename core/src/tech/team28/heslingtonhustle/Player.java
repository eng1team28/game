package tech.team28.heslingtonhustle;

public class Player {

    private MoveComponent moveComponent;
    private float interactRadius = 150;

    private float energy;

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float newValue) {
        energy = newValue;
    }

    public Player() {
        moveComponent = new MoveComponent();
    }
}

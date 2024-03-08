package tech.team28.heslingtonhustle;

import com.badlogic.gdx.math.MathUtils;

public class Player {

    private MoveComponent moveComponent;
    private float interactRadius = 150;

    private float energy;
    private float maxEnergy;

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = MathUtils.clamp(energy, 0, maxEnergy);
    }

    public Player() {
        energy = 100;
        maxEnergy = 100;
        moveComponent = new MoveComponent();
    }
}

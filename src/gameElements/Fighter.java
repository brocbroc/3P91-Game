package gameElements;

import utility.*;

public abstract class Fighter extends Inhabitant implements Damager {
    protected int baseDamage;
    protected int hitPoints;

    public Fighter(int baseDamage, int hitPoints) {
        super();
        this.baseDamage = baseDamage;
        this.hitPoints = hitPoints;
        this.position = new Position(0, 0);
    }

    @Override
    public int damage() {
        return baseDamage + (2 * (level - 1));
    }

    public abstract Cost getTrainCost();
}
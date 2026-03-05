package gameElements;

import utility.Position;

/**
 * Base class for combat units.
 */
public abstract class Fighter extends Inhabitant implements Damager {
    protected int health;
    protected int baseDamage;

    public Fighter(int damage, int health) {
        super();
        this.baseDamage = damage;
        this.health = health;
        position = new Position(0,0);
    }

    @Override
    public int damage() {
        return baseDamage + (level * 2);
    }

    public int getHealth() {
        return health;
    }
}
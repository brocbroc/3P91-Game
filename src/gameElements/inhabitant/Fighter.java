package gameElements.inhabitant;

import gameElements.Damager;
import utility.*;

/**
 * Abstract class representing a combat unit in the village army.
 * Fighters can deal damage and have hit points.
 */
public abstract class Fighter extends Inhabitant implements Damager {
    protected int baseDamage;
    protected int hitPoints;

    /**
     * Creates a new fighter with a base damage and hit points.
     *
     * @param baseDamage the base damage inflicted by the unit
     * @param hitPoints the base hit points of the unit
     */
    public Fighter(int baseDamage, int hitPoints) {
        super();
        this.baseDamage = baseDamage;
        this.hitPoints = hitPoints;
    }

    /**
     * Calculates the damage inflicted by the fighter.
     * Damage increases slightly with level.
     */
    @Override
    public int damage() {
        return baseDamage;
    }

    /**
     * Returns the hit points of the fighter.
     */
    public int getHitPoints() {
        return hitPoints;
    }
}
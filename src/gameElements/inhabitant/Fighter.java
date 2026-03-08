package gameElements.inhabitant;

import gameElements.Damager;
import utility.*;

/**
 * Abstract class representing a combat unit in the village army.
 * Fighters can deal damage and have hit points.
 */
public abstract class Fighter extends Inhabitant implements Damager {
    protected int remainingHitPoints;
    protected boolean isDead;

    /**
     * Class constructor.
     */
    public Fighter() {
        super();
        isDead = false;
    }

    /**
     * Returns the remaining hit points
     * @return the remaining hit points
     */
    public int getRemainingHitPoints() {
        return remainingHitPoints;
    }

    /**
     * Return whether or not the unit is dead
     * @return <code>true</code> if the unit is dead, <code>false</code> if the unit is not dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Restores the hit points and sets <code>isDead</code> to false
     */
    public abstract void restore();
}
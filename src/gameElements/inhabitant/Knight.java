package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a heavily armored knight unit.
 */
public class Knight extends Fighter {
    private KnightData data;

    /**
     * Class constructor.
     * @param data the knight data of the village
     */
    public Knight(KnightData data) {
        super();
        this.data = data;
        remainingHitPoints = data.getHitPoints();
    }

    /**
     * Restores the hit points and sets <code>isDead</code> to false
     */
    @Override
    public void restore() {
        remainingHitPoints = data.getHitPoints();
        isDead = false;
    }
}
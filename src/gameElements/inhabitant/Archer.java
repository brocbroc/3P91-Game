package gameElements.inhabitant;

/**
 * Represents an archer unit with ranged attack capability.
 */
public class Archer extends Fighter {
    private ArcherData data;

    /**
     * Class constructor.
     */
    public Archer(ArcherData data) {
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
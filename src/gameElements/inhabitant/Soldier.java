package gameElements.inhabitant;

/**
 * Represents a basic soldier unit in the army.
 */
public class Soldier extends Fighter {
    private SoldierData data;

    /**
     * Class constructor.
     */
    public Soldier(SoldierData data) {
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
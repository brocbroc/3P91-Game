package gameElements.inhabitant;

/**
 * Represents a siege unit used to damage buildings.
 */
public class Catapult extends Fighter {
    private CatapultData data;

    /**
     * Class constructor.
     */
    public Catapult(CatapultData data) {
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

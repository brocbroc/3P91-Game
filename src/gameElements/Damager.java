package gameElements;

/**
 * Represents an object capable of dealing damage.
 * This interface is used by army units and defence buildings.
 */
public interface Damager {

    /**
     * Returns the amount of damage inflicted.
     *
     * @return damage value
     */
    int damage();
}
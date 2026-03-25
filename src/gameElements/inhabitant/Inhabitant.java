package gameElements.inhabitant;

import utility.Cost;
import utility.Position;

/**
 * Abstract class representing an inhabitant of a village.
 * Each inhabitant subclass can be upgraded.
 */
public abstract class Inhabitant {
    protected static final int MAX_LEVEL = 4;
    protected Position position;

    /**
     * Class constructor.
     */
    public Inhabitant() {
        position = new Position(0, 0); // Spawn point
    }

    /**
     * Returns the maximum upgrade level for inhabitant subclasses.
     * @return the maximum upgrade level
     */
    public static int getMaxLevel() {
        return MAX_LEVEL;
    }

    /**
     * Returns the position of the inhabitant.
     * @return the current position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the inhabitant.
     * @param pos the new position
     */
    public void setPosition(Position pos) {
        position = pos;
    }
}
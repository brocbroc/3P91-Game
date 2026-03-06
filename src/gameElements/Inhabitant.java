package gameElements;

import utility.Position;

/**
 * Abstract class representing an inhabitant of a village.
 * Inhabitants can be upgraded and occupy positions in the village.
 */
public abstract class Inhabitant {

    protected Position position;
    protected Position newPosition;
    protected int level;

    /**
     * Creates a new inhabitant starting at level 1.
     */
    public Inhabitant() {
        level = 1;
    }

    /**
     * Returns the level of the inhabitant.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Upgrades the inhabitant to the next level.
     */
    public void upgrade() {
        level++;
    }

    /**
     * Returns the current position of the inhabitant.
     */
    public Position getPosition() {
        return position;
    }
}
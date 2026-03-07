package gameElements;

import utility.Cost;

/**
 * Represents a basic soldier unit in the army.
 */
public class Soldier extends Fighter {

    /**
     * Creates a soldier with predefined damage and health.
     */
    public Soldier() {
        super(6, 40);
    }

    /**
     * Returns the cost required to train the soldier.
     */
    @Override
    public Cost getTrainCost() {
        return new Cost(10, 10, 5);
    }

    @Override
    public String toString() {
        return "Soldier (L" + level + ")";
    }
}
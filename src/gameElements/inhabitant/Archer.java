package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents an archer unit with ranged attack capability.
 */
public class Archer extends Fighter {
    private static final Cost PRODUCTION_COST = new Cost(0, 10, 20);

    /**
     * Class constructor.
     */
    public Archer() {
        super(7, 30);
    }

    /**
     * Returns the production cost of an archer
     * @return the production cost
     */
    public static Cost getProductionCost() { return PRODUCTION_COST; }
}
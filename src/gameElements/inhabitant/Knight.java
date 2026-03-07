package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a heavily armored knight unit.
 */
public class Knight extends Fighter {
    private static final Cost PRODUCTION_COST = new Cost(10, 20, 10);

    /**
     * Class constructor.
     */
    public Knight() {
        super(10, 60);
    }

    /**
     * Returns the production cost of a soldier
     * @return the production cost
     */
    public static Cost getProductionCost() { return PRODUCTION_COST; }
}
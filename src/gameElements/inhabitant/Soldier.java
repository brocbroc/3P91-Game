package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a basic soldier unit in the army.
 */
public class Soldier extends Fighter {
    private static final Cost PRODUCTION_COST = new Cost(0, 20, 10);

    /**
     * Class constructor.
     */
    public Soldier() {
        super(6, 40);
    }

    /**
     * Returns the production cost of a soldier
     * @return the production cost
     */
    public static Cost getProductionCost() { return PRODUCTION_COST; }
}
package gameElements.inhabitant;

import utility.Cost;

/**
 * Represents a siege unit used to damage buildings.
 */
public class Catapult extends Fighter {
    private static final Cost PRODUCTION_COST = new Cost(20, 20, 20);

    /**
     * Class constructor.
     */
    public Catapult() {
        super(14, 50);
    }

    /**
     * Returns the production cost of a soldier
     * @return the production cost
     */
    public static Cost getProductionCost() { return PRODUCTION_COST; }
}

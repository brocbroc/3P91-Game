package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Lumberman;

/**
 * This class creates a lumberman.
 */
public class LumbermanConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a lumberman
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Lumberman.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Lumberman.getProductionTime();
	}

	/**
	 * Creates a new lumberman
	 * @return the new lumberman
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Lumberman();
	}
}

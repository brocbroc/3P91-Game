package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Lumberman;

/**
 * This class creates lumbermen.
 */
public class LumbermanConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a lumberman
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return Lumberman.getProductionCost(); }

	/**
	 * Creates a new lumberman
	 * @return the new lumberman
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Lumberman();
	}
}

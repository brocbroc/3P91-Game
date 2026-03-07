package utility;

import gameElements.inhabitant.Catapult;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs a catapult.
 */
public class CatapultConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a catapult
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return Catapult.getProductionCost(); }

	/**
	 * Creates a new catapult
	 * @return the new catapult
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Catapult();
	}
}

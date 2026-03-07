package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Soldier;

/**
 * This class constructs soldiers.
 */
public class SoldierConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a soldier
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return Soldier.getProductionCost(); }

	/**
	 * Creates a new soldier
	 * @return the new soldier
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Soldier();
	}
}

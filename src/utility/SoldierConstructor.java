package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Soldier;

/**
 * This class constructs a soldier.
 */
public class SoldierConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a soldier
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Soldier.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Soldier.getProductionTime();
	}

	/**
	 * Creates a new soldier
	 * @return the new soldier
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Soldier();
	}
}

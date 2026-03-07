package utility;

import gameElements.inhabitant.Archer;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs an archer.
 */
public class ArcherConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing an archer
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return Archer.getProductionCost(); }

	/**
	 * Creates a new archer
	 * @return the new archer
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Archer();
	}
}

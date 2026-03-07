package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Knight;

/**
 * This class constructs a knight.
 */
public class KnightConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a knight
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return Knight.getProductionCost(); }

	/**
	 * Creates a new knight
	 * @return the new knight
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Knight();
	}
}

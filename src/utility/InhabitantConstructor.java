package utility;

import gameElements.inhabitant.Inhabitant;

/**
 * This interface constructs an Inhabitant.
 */
public interface InhabitantConstructor {
	/**
	 * Return the cost of producing an inhabitant
	 * @return the production cost
	 */
	Cost getProductionCost();

	/**
	 * Creates a new inhabitant
	 * @return the new inhabitant
	 */
	Inhabitant addInhabitant();
}

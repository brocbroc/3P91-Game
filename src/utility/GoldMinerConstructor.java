package utility;

import gameElements.inhabitant.GoldMiner;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs gold miners.
 */
public class GoldMinerConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a gold miner
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() { return GoldMiner.getProductionCost(); }

	/**
	 * Creates a new gold miner
	 * @return the new gold miner
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new GoldMiner();
	}
}

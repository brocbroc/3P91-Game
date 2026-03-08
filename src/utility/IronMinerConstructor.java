package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.IronMiner;

/**
 * This class constructs an iron miner.
 */
public class IronMinerConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing an iron miner
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return IronMiner.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return IronMiner.getProductionTime();
	}

	/**
	 * Creates a new iron miner
	 * @return the new iron miner
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new IronMiner();
	}
}

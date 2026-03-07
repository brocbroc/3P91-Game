package gameElements.inhabitant;

import utility.Cost;
import utility.Position;

/**
 * This class represents an iron miner. They produce iron at iron mines.
 */
public class IronMiner extends Inhabitant implements Peasant {
	private static final Cost PRODUCTION_COST = new Cost(5, 20, 5);
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public IronMiner() {
		super();
		position = new Position(0, 0); // Spawn point
		isBusy = false;
	}

	/**
	 * Returns the production cost of an iron miner
	 * @return the production cost
	 */
	public static Cost getProductionCost() { return PRODUCTION_COST; }

	/**
	 * Checks if the iron miner is busy.
	 * @return <code>true</code> if the iron miner is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() { return isBusy; }

	/**
	 * Sets whether or not the iron miner is busy.
	 * @param isBusy the state of the iron miner
	 */
	@Override
	public void setBusy(boolean isBusy) { this.isBusy = isBusy; }
}

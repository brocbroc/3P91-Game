package gameElements.inhabitant;

import utility.Cost;
import utility.Position;

/**
 * This class represents a gold miner. They produce gold at gold mines.
 */
public class GoldMiner extends Inhabitant implements Peasant {
	private static final Cost PRODUCTION_COST = new Cost(20, 5, 5);
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public GoldMiner() {
		super();
		position = new Position(0, 0); // Spawn point
		isBusy = false;
	}

	/**
	 * Returns the production cost of a gold miner
	 * @return the production cost
	 */
	public static Cost getProductionCost() { return PRODUCTION_COST; }

	/**
	 * Checks if the gold miner is busy.
	 * @return <code>true</code> if the gold miner is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() { return isBusy; }

	/**
	 * Sets whether or not the gold miner is busy.
	 * @param isBusy the state of the gold miner
	 */
	@Override
	public void setBusy(boolean isBusy) { this.isBusy = isBusy; }
}

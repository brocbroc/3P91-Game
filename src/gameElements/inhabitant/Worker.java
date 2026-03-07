package gameElements.inhabitant;

import utility.Cost;
import utility.Position;

/**
 * This class represents a worker. Workers are responsible for construction.
 */
public class Worker extends Inhabitant implements Peasant {
	private static final Cost PRODUCTION_COST = new Cost(0, 10, 10);
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public Worker() {
		super();
		position = new Position(0, 0); // Spawn point
		isBusy = false;
	}

	/**
	 * Returns the production cost of a worker
	 * @return the production cost
	 */
	public static Cost getProductionCost() { return PRODUCTION_COST; }

	/**
	 * Checks if the worker is busy.
	 * @return <code>true</code> if the worker is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() { return isBusy; }

	/**
	 * Sets whether or not the worker is busy.
	 * @param isBusy the state of the worker
	 */
	@Override
	public void setBusy(boolean isBusy) { this.isBusy = isBusy; }

	// workers refund materials when upgraded
}

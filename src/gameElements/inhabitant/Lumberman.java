package gameElements.inhabitant;

import utility.Cost;
import utility.Position;

/**
 * This class represents a lumberman. They can produce lumber at a lumber mill.
 */
public class Lumberman extends Inhabitant implements Peasant {
	private static final Cost PRODUCTION_COST = new Cost(5, 5, 20);
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public Lumberman() {
		super();
		position = new Position(0, 0); // Spawn point
		isBusy = false;
	}

	/**
	 * Returns the production cost of a lumberman
	 * @return the production cost
	 */
	public static Cost getProductionCost() { return PRODUCTION_COST; }

	/**
	 * Checks if the lumberman is busy.
	 * @return <code>true</code> if the lumberman is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() { return isBusy; }

	/**
	 * Sets whether or not the lumberman is busy.
	 * @param isBusy the state of the lumberman
	 */
	@Override
	public void setBusy(boolean isBusy) { this.isBusy = isBusy; }
}

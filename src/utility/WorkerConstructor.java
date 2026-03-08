package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Worker;

/**
 * This class creates a worker.
 */
public class WorkerConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a worker
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Worker.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Worker.getProductionTime();
	}

	/**
	 * Creates a new worker
	 * @return the new worker
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Worker();
	}

	/**
	 * Returns the level of the Worker class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return Worker.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return Worker.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		Worker.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return Worker.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return Worker.getUpgradeTime();
	}

	/**
	 * Upgrades the Worker class
	 */
	@Override
	public void upgrade() {
		Worker.upgrade();
	}
}

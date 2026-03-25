package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Worker;
import gameElements.inhabitant.WorkerData;

/**
 * This class creates a worker.
 */
public class WorkerConstructor implements InhabitantConstructor {
	private WorkerData data;

	/**
	 * Class constructor
	 * @param data the worker data of the village
	 */
	public WorkerConstructor(WorkerData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing a worker
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return WorkerData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return WorkerData.getProductionTime();
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
		return data.getLevel();
	}

	/**
	 * Returns the maximum upgrade level of the Worker class
	 * @return the maximum level
	 */
	@Override
	public int getMaxLevel() {
		return data.getMaxLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return data.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		data.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return data.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return data.getUpgradeTime();
	}

	/**
	 * Upgrades the Worker class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

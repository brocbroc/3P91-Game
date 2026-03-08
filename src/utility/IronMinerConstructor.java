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

	/**
	 * Returns the level of the IronMiner class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return IronMiner.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return IronMiner.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		IronMiner.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return IronMiner.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return IronMiner.getUpgradeTime();
	}

	/**
	 * Upgrades the IronMiner class
	 */
	@Override
	public void upgrade() {
		IronMiner.upgrade();
	}
}

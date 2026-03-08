package utility;

import gameElements.inhabitant.GoldMiner;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs a gold miner.
 */
public class GoldMinerConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a gold miner
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return GoldMiner.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return GoldMiner.getProductionTime();
	}

	/**
	 * Creates a new gold miner
	 * @return the new gold miner
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new GoldMiner();
	}

	/**
	 * Returns the level of the GoldMiner class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return GoldMiner.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return GoldMiner.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		GoldMiner.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return GoldMiner.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return GoldMiner.getUpgradeTime();
	}

	/**
	 * Upgrades the GoldMiner class
	 */
	@Override
	public void upgrade() {
		GoldMiner.upgrade();
	}
}

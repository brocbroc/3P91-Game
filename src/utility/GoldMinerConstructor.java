package utility;

import gameElements.inhabitant.GoldMiner;
import gameElements.inhabitant.GoldMinerData;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs a gold miner.
 */
public class GoldMinerConstructor implements InhabitantConstructor {
	private GoldMinerData data;

	/**
	 * Class constructor
	 * @param data the gold miner data of the village
	 */
	public GoldMinerConstructor(GoldMinerData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing a gold miner
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return GoldMinerData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return GoldMinerData.getProductionTime();
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
		return data.getLevel();
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
	 * Upgrades the GoldMiner class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.IronMiner;
import gameElements.inhabitant.IronMinerData;

/**
 * This class constructs an iron miner.
 */
public class IronMinerConstructor implements InhabitantConstructor {
	private IronMinerData data;

	/**
	 * Class constructor
	 * @param data the iron miner data of a village
	 */
	public IronMinerConstructor(IronMinerData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing an iron miner
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return IronMinerData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return IronMinerData.getProductionTime();
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
		return data.getLevel();
	}

	/**
	 * Returns the maximum upgrade level of the Iron Miner class
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
	 * Upgrades the IronMiner class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

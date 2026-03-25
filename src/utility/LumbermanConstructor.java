package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Lumberman;
import gameElements.inhabitant.LumbermanData;

/**
 * This class creates a lumberman.
 */
public class LumbermanConstructor implements InhabitantConstructor {
	private LumbermanData data;

	/**
	 * Class constructor
	 * @param data the lumberman data of the village
	 */
	public LumbermanConstructor(LumbermanData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing a lumberman
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return LumbermanData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return data.getProductionTime();
	}

	/**
	 * Creates a new lumberman
	 * @return the new lumberman
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Lumberman();
	}

	/**
	 * Returns the level of the Lumberman class
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
	 * Upgrades the Lumberman class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

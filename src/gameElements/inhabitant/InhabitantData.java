package gameElements.inhabitant;

import utility.Cost;

/**
 * This class represents a village's data of a subtype of inhabitant.
 */
public abstract class InhabitantData {
	protected int maxLevel;
	protected int level;
	protected boolean isUpgrading;

	/**
	 * Class constructor
	 */
	public InhabitantData() {
		maxLevel = 0;
		level = 0;
		isUpgrading = false;
	}

	/**
	 * Returns the maximum upgrade level
	 * @return the maximum level
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	public abstract Cost getUpgradeCost();

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	public abstract int getUpgradeTime();

	/**
	 * Returns the level of the class
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Returns whether or not the class is upgrading.
	 * @return <code>true</code> if the class is upgrading, <code>false</code> otherwise
	 */
	public boolean isUpgrading() {
		return isUpgrading;
	}

	/**
	 * Sets the maximum upgrade level
	 * @param level the new maximum level
	 */
	public void setMaxLevel(int level) {
		maxLevel = level;
	}

	/**
	 * Sets whether or not the class is upgrading
	 * @param upgrading the new upgrade status
	 */
	public void setUpgrading(boolean upgrading) {
		isUpgrading = upgrading;
	}

	/**
	 * Upgrades the Inhabitant class
	 */
	public abstract void upgrade();
}

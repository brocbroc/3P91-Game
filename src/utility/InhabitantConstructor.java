package utility;

import gameElements.inhabitant.Inhabitant;

/**
 * This interface constructs an Inhabitant.
 */
public interface InhabitantConstructor {
	/**
	 * Return the cost of producing an inhabitant
	 * @return the production cost
	 */
	Cost getProductionCost();

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	int getProductionTime();

	/**
	 * Creates a new inhabitant
	 * @return the new inhabitant
	 */
	Inhabitant addInhabitant();

	/**
	 * Returns the level of the inhabitant class
	 * @return the current level
	 */
	int getLevel();

	/**
	 * Returns the maximum upgrade level of the inhabitant class
	 * @return the maximum level
	 */
	int getMaxLevel();

	/**
	 * Returns whether or not the inhabitant class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	boolean isUpgrading();

	/**
	 * Set whether or not the inhabitant class is upgrading
	 * @param upgrading the upgrading status
	 */
	void setUpgrading(boolean upgrading);

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	Cost getUpgradeCost();

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	int getUpgradeTime();

	/**
	 * Upgrades the inhabitant class
	 */
	void upgrade();
}

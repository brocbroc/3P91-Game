package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Knight;

/**
 * This class constructs a knight.
 */
public class KnightConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a knight
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Knight.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Knight.getProductionTime();
	}

	/**
	 * Creates a new knight
	 * @return the new knight
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Knight();
	}

	/**
	 * Returns the level of the Knight class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return Knight.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return Knight.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		Knight.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return Knight.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return Knight.getUpgradeTime();
	}

	/**
	 * Upgrades the Knight class
	 */
	@Override
	public void upgrade() {
		Knight.upgrade();
	}
}

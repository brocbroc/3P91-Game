package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Lumberman;

/**
 * This class creates a lumberman.
 */
public class LumbermanConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a lumberman
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Lumberman.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Lumberman.getProductionTime();
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
		return Lumberman.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return Lumberman.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		Lumberman.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return Lumberman.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return Lumberman.getUpgradeTime();
	}

	/**
	 * Upgrades the Lumberman class
	 */
	@Override
	public void upgrade() {
		Lumberman.upgrade();
	}
}

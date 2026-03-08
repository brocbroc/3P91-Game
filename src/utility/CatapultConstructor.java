package utility;

import gameElements.inhabitant.Catapult;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs a catapult.
 */
public class CatapultConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a catapult
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Catapult.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Catapult.getProductionTime();
	}

	/**
	 * Creates a new catapult
	 * @return the new catapult
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Catapult();
	}

	/**
	 * Returns the level of the Catapult class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return Catapult.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return Catapult.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		Catapult.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return Catapult.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return Catapult.getUpgradeTime();
	}

	/**
	 * Upgrades the Catapult class
	 */
	@Override
	public void upgrade() {
		Catapult.upgrade();
	}
}

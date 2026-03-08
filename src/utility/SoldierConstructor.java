package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Soldier;

/**
 * This class constructs a soldier.
 */
public class SoldierConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing a soldier
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Soldier.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Soldier.getProductionTime();
	}

	/**
	 * Creates a new soldier
	 * @return the new soldier
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Soldier();
	}

	/**
	 * Returns the level of the Soldier class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return Soldier.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return Soldier.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		Soldier.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return Soldier.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return Soldier.getUpgradeTime();
	}

	/**
	 * Upgrades the Soldier class
	 */
	@Override
	public void upgrade() {
		Soldier.upgrade();
	}
}

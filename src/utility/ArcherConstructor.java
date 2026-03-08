package utility;

import gameElements.inhabitant.Archer;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs an archer.
 */
public class ArcherConstructor implements InhabitantConstructor {
	/**
	 * Return the cost of producing an archer
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return Archer.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return Archer.getProductionTime();
	}

	/**
	 * Creates a new archer
	 * @return the new archer
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Archer();
	}

	/**
	 * Returns the level of the Archer class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return Archer.getLevel();
	}

	/**
	 * Returns whether or not the class is upgrading
	 * @return <code>true</code> if the class is upgrading, <code>false</code> if not
	 */
	@Override
	public boolean isUpgrading() {
		return Archer.isUpgrading();
	}

	/**
	 * Set whether or not the class is upgrading
	 * @param upgrading the upgrading status
	 */
	@Override
	public void setUpgrading(boolean upgrading) {
		Archer.setUpgrading(upgrading);
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	@Override
	public Cost getUpgradeCost() {
		return Archer.getUpgradeCost();
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		return Archer.getUpgradeTime();
	}

	/**
	 * Upgrades the Archer class
	 */
	@Override
	public void upgrade() {
		Archer.upgrade();
	}
}

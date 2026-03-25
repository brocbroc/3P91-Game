package utility;

import gameElements.inhabitant.Catapult;
import gameElements.inhabitant.CatapultData;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs a catapult.
 */
public class CatapultConstructor implements InhabitantConstructor {
	private CatapultData data;

	/**
	 * Class constructor
	 * @param data the catapult data
	 */
	public CatapultConstructor(CatapultData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing a catapult
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return CatapultData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return CatapultData.getProductionTime();
	}

	/**
	 * Creates a new catapult
	 * @return the new catapult
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Catapult(data);
	}

	/**
	 * Returns the level of the Catapult class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return data.getLevel();
	}

	/**
	 * Returns the maximum upgrade level of the Catapult class
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
	 * Upgrades the Catapult class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

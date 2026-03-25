package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Soldier;
import gameElements.inhabitant.SoldierData;

/**
 * This class constructs a soldier.
 */
public class SoldierConstructor implements InhabitantConstructor {
	private SoldierData data;

	/**
	 * Class constructor
	 * @param data the soldier data for the village
	 */
	public SoldierConstructor(SoldierData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing a soldier
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return SoldierData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return SoldierData.getProductionTime();
	}

	/**
	 * Creates a new soldier
	 * @return the new soldier
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Soldier(data);
	}

	/**
	 * Returns the level of the Soldier class
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
	 * Upgrades the Soldier class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

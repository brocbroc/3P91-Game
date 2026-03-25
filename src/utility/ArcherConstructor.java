package utility;

import gameElements.inhabitant.Archer;
import gameElements.inhabitant.ArcherData;
import gameElements.inhabitant.Inhabitant;

/**
 * This class constructs an archer.
 */
public class ArcherConstructor implements InhabitantConstructor {
	private ArcherData data;

	/**
	 * Class constructor
	 * @param data the archer data of the village
	 */
	public ArcherConstructor(ArcherData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing an archer
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return ArcherData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return ArcherData.getProductionTime();
	}

	/**
	 * Creates a new archer
	 * @return the new archer
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Archer(data);
	}

	/**
	 * Returns the level of the Archer class
	 * @return the current level
	 */
	@Override
	public int getLevel() {
		return data.getLevel();
	}

	/**
	 * Returns the maximum upgrade level of the Archer class
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
	 * Upgrades the Archer class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

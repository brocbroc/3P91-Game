package utility;

import gameElements.inhabitant.Inhabitant;
import gameElements.inhabitant.Knight;
import gameElements.inhabitant.KnightData;

/**
 * This class constructs a knight.
 */
public class KnightConstructor implements InhabitantConstructor {
	private KnightData data;

	/**
	 * Class constructor
	 * @param data the knight data
	 */
	public KnightConstructor(KnightData data) {
		this.data = data;
	}

	/**
	 * Return the cost of producing a knight
	 * @return the production cost
	 */
	@Override
	public Cost getProductionCost() {
		return KnightData.getProductionCost();
	}

	/**
	 * Return the production time
	 * @return the production time, in seconds
	 */
	@Override
	public int getProductionTime() {
		return KnightData.getProductionTime();
	}

	/**
	 * Creates a new knight
	 * @return the new knight
	 */
	@Override
	public Inhabitant addInhabitant() {
		return new Knight(data);
	}

	/**
	 * Returns the level of the Knight class
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
	 * Upgrades the Knight class
	 */
	@Override
	public void upgrade() {
		data.upgrade();
	}
}

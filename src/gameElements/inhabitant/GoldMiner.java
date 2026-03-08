package gameElements.inhabitant;

import utility.Cost;

/**
 * This class represents a gold miner. They produce gold at gold mines.
 */
public class GoldMiner extends Inhabitant implements Peasant {
	private static final Cost PRODUCTION_COST;
	private static final int PRODUCTION_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;
	private static int level;
	private static boolean isUpgrading;
	private static int mineRate; // per 10 seconds
	private boolean isBusy;

	static {
		PRODUCTION_COST = new Cost(20, 5, 5);
		PRODUCTION_TIME = 15;
		UPGRADE_COSTS = new Cost[] {
			new Cost(40, 10, 10),
			new Cost(80, 20, 20),
			new Cost(160, 40, 40),
			new Cost(320, 80, 80)
		};
		UPGRADE_TIMES = new int[] { 20, 25, 30, 35 };
		level = 0;
		mineRate = 1;
	}

	/**
	 * Class constructor.
	 */
	public GoldMiner() {
		super();
		isBusy = false;
	}

	/**
	 * Returns the production cost
	 * @return the production cost
	 */
	public static Cost getProductionCost() {
		return PRODUCTION_COST;
	}

	/**
	 * Returns the production time
	 * @return the production time, in seconds
	 */
	public static int getProductionTime() {
		return PRODUCTION_TIME;
	}

	/**
	 * Returns the upgrade cost
	 * @return the upgrade cost
	 */
	public static Cost getUpgradeCost() {
		if (level < Inhabitant.MAX_LEVEL) {
			return UPGRADE_COSTS[level];
		}

		return null;
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	public static int getUpgradeTime() {
		if (level < Inhabitant.MAX_LEVEL) {
			return UPGRADE_TIMES[level];
		}

		return 0;
	}

	/**
	 * Returns the level of the class
	 * @return the level
	 */
	public static int getLevel() {
		return level;
	}

	/**
	 * Returns whether or not the class is upgrading.
	 * @return <code>true</code> if the class is upgrading, <code>false</code> otherwise
	 */
	public static boolean isUpgrading() {
		return isUpgrading;
	}

	/**
	 * Sets whether or not the class is upgrading
	 * @param upgrading the new upgrade status
	 */
	public static void setUpgrading(boolean upgrading) {
		isUpgrading = upgrading;
	}

	/**
	 * Checks if the gold miner is busy.
	 * @return <code>true</code> if the gold miner is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() {
		return isBusy;
	}

	/**
	 * Sets whether or not the gold miner is busy.
	 * @param isBusy the state of the gold miner
	 */
	@Override
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	/**
	 * Upgrades the IronMiner class
	 */
	public static void upgrade() {
		level++;
		mineRate++;
	}
}

package gameElements.inhabitant;

import utility.Cost;

/**
 * This class contains the information about the lumbermen of a village.
 * It contains the production cost and time, the upgrade cost and time, the level, and the
 * upgrading status.
 */
public class LumbermanData extends InhabitantData {
	private static final Cost PRODUCTION_COST;
	private static final int PRODUCTION_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;
	private int mineRate; // per 10 seconds

	static {
		PRODUCTION_COST = new Cost(5, 5, 20);
		PRODUCTION_TIME = 5;
		UPGRADE_COSTS = new Cost[] {
			new Cost(10, 10, 40),
			new Cost(20, 20, 80),
			new Cost(40, 40, 160),
			new Cost(80, 80, 320)
		};
		UPGRADE_TIMES = new int[] { 10, 15, 20, 25 };
	}

	/**
	 * Class constructor
	 */
	public LumbermanData() {
		super();
		mineRate = 1;
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
	@Override
	public Cost getUpgradeCost() {
		if (level < Inhabitant.MAX_LEVEL) {
			return UPGRADE_COSTS[level];
		}

		return null;
	}

	/**
	 * Returns the upgrade time
	 * @return the upgrade time, in seconds
	 */
	@Override
	public int getUpgradeTime() {
		if (level < Inhabitant.MAX_LEVEL) {
			return UPGRADE_TIMES[level];
		}

		return 0;
	}

	/**
	 * Upgrades the Lumberman class
	 */
	@Override
	public void upgrade() {
		level++;
		mineRate++;
	}
}

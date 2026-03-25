package gameElements.inhabitant;

import utility.Cost;

/**
 * This class contains the information about the catapults of a village.
 * It contains the production cost and time, the upgrade cost and time, the level, and the
 * upgrading status.
 */
public class CatapultData extends InhabitantData implements FighterData {
	private static final Cost PRODUCTION_COST;
	private static final int PRODUCTION_TIME; // seconds
	private static final Cost[] UPGRADE_COSTS;
	private static final int[] UPGRADE_TIMES;
	private int hitPoints;
	private int damage;
	private int range;

	static {
		PRODUCTION_COST = new Cost(20, 20, 20);
		PRODUCTION_TIME = 20;
		UPGRADE_COSTS = new Cost[] {
			new Cost(40, 40, 40),
			new Cost(80, 80, 80),
			new Cost(160, 160, 160),
			new Cost(320, 320, 320)
		};
		UPGRADE_TIMES = new int[] { 25, 30, 35, 40 };
	}

	/**
	 * Class constructor
	 */
	public CatapultData() {
		super();
		hitPoints = 500;
		damage = 20;
		range = 10;
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
	 * Returns the total hit points
	 * @return the total hit points
	 */
	@Override
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Returns the damage done
	 * @return the damage done
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/**
	 * Returns the range of attack
	 * @return the range
	 */
	@Override
	public int getRange() {
		return range;
	}

	/**
	 * Upgrades the Soldier class
	 */
	@Override
	public void upgrade() {
		level++;
		hitPoints += 50;
		damage += 5;
	}
}

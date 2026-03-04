package game;

import utility.*;

/**
 * This class represents the inventory of a village.
 * The amount of each resource cannot drop below 0.
 */
public class Inventory {
	private int maxGold;
	private int maxIron;
	private int maxLumber;
	private int gold;
	private int iron;
	private int lumber;

	/**
	 * Class constructor.
	 * @param gold the starting amount of gold
	 * @param iron the starting amount of iron
	 * @param lumber the starting amount of lumber
	 */
	public Inventory(int gold, int iron, int lumber) {
		maxGold = 1000;
		maxIron = 1000;
		maxLumber = 1000;
		this.gold = 0;
		this.iron = 0;
		this.lumber = 0;
		this.addGold(gold);
		this.addIron(iron);
		this.addLumber(lumber);
	}

	/**
	 * Returns the amount of gold in the inventory
	 * @return the amount of gold
	 */
	public int getGold() { return gold; }

	/**
	 * Returns the amount of iron in the inventory
	 * @return the amount of iron
	 */
	public int getIron() { return iron; }

	/**
	 * Returns the amount of lumber in the inventory
	 * @return the amount of lumber
	 */
	public int getLumber() { return lumber; }

	/**
	 * Checks if a cost can be covered by the inventory.
	 * @param cost the cost to check
	 * @return <code>true</code> if the cost can be covered, <code>false</code> otherwise
	 */
	public boolean checkCost(Cost cost) {
		return cost.GOLD <= gold && cost.IRON <= iron && cost.LUMBER <= lumber;
	}

	/**
	 * Adds gold to the inventory, capped at the maximum.
	 * @param gold the amount of gold, should be positive
	 * @throws IllegalArgumentException if the argument <code>gold</code> is negative
	 */
	public void addGold(int gold) {
		if (gold < 0) {
			throw new IllegalArgumentException("Input cannot be negative.");
		}

		this.gold += gold;

		if (this.gold > maxGold) {
			this.gold = maxGold;
		}
	}

	/**
	 * Adds iron to the inventory, capped at the maximum.
	 * @param iron the amount of iron, should be positive
	 * @throws IllegalArgumentException if the argument <code>iron</code> is negative
	 */
	public void addIron(int iron) {
		if (iron < 0) {
			throw new IllegalArgumentException("Input cannot be negative.");
		}

		this.iron += iron;

		if (this.iron > maxIron) {
			this.iron = maxIron;
		}
	}

	/**
	 * Adds lumber to the inventory, capped at the maximum.
	 * @param lumber the amount of lumber, should be positive
	 * @throws IllegalArgumentException if the argument <code>lumber</code> is negative
	 */
	public void addLumber(int lumber) {
		if (lumber < 0) {
			throw new IllegalArgumentException("Input cannot be negative.");
		}

		this.lumber += lumber;

		if (this.lumber > maxLumber) {
			this.lumber = maxLumber;
		}
	}

	/**
	 * Pays a cost from the inventory.
	 * @param cost the cost to be paid
	 * @throws RuntimeException if the amount of any resource is less than 0
	 */
	public void payCost(Cost cost) {
		this.gold -= cost.GOLD;
		this.iron -= cost.IRON;
		this.lumber -= cost.LUMBER;

		if (gold < 0 || iron < 0 || lumber < 0) {
			throw new RuntimeException("Inventory cannot have negative values.");
		}
	}
}

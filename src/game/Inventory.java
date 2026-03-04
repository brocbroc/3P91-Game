package game;

import utility.*;

/**
 * This class represents the inventory of a village.
 * The amount of each resource cannot drop below 0.
 */
public class Inventory {
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
		this.gold = gold;
		this.iron = iron;
		this.lumber = lumber;
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
	 * Adds iron to the inventory.
	 * @param gold the amount of gold
	 */
	public void addGold(int gold) { this.gold += gold; }

	/**
	 * Adds iron to the inventory.
	 * @param iron the amount of iron
	 */
	public void addIron(int iron) { this.iron += iron; }

	/**
	 * Adds lumber to the inventory.
	 * @param lumber the amount of lumber
	 */
	public void addLumber(int lumber) { this.lumber += lumber; }

	/**
	 * Pays a cost from the inventory.
	 * @param cost the cost to be paid
	 */
	public void payCost(Cost cost) {
		this.gold -= cost.GOLD;
		this.iron -= cost.IRON;
		this.lumber -= cost.LUMBER;
	}
}

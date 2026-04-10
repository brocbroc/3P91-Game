package client;

import java.io.Serializable;

/**
 * This class represents a snapshot of the village's current state.
 * It is used to give the client data for displaying.
 */
public class VillageSnapshot implements Serializable {
	private String[][] map;
	private int[] inventory;
	private int[] inhabitantLevels;
	private int[] inhabitantCounts;

	/**
	 * Class constructor.
	 * @param map a string representation of the village map
	 * @param inventory the inventory status
	 * @param inhabitantLevels the level of each inhabitant type
	 * @param inhabitantCounts the count of each inhabitant type
	 */
	public VillageSnapshot(String[][] map, int[] inventory, int[] inhabitantLevels, int[] inhabitantCounts) {
		this.map = map;
		this.inventory = inventory;
		this.inhabitantLevels = inhabitantLevels;
		this.inhabitantCounts = inhabitantCounts;
	}

	/**
	 * Returns the string representation of the village map
	 * @return the map
	 */
	public String[][] getMap() {
		return map;
	}

	/**
	 * Returns the inventory counts
	 * @return the inventory
	 */
	public int[] getInventory() {
		return inventory;
	}

	/**
	 * Returns the levels of the inhabitant types
	 * @return the inhabitant levels
	 */
	public int[] getInhabitantLevels() {
		return inhabitantLevels;
	}

	/**
	 * Returns the number of each inhabitant type
	 * @return the number of each inhabitant by type
	 */
	public int[] getInhabitantCounts() {
		return inhabitantCounts;
	}
}

package gameElements.building;

/**
 * This class holds the data of a specific type of building.
 */
public abstract class BuildingData {
	protected int maxLevel;
	protected int count;
	protected int maxCount;

	/**
	 * Class constructor
	 */
	public BuildingData() {
		maxLevel = 0;
		count = 0;
	}

	/**
	 * Returns the maximum possible level of a building.
	 * @return the maximum possible level
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * Returns the current number of buildings of this type
	 * @return the number of buildings
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the maximum number of buildings of this type
	 * @return the maximum number of buildings
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * Increments the count when a new building is added
	 */
	void incrementCount() {
		count++;
	}

	/**
	 * Sets the maximum upgrade level
	 * @param level the maximum upgrade level
	 */
	void setMaxLevel(int level) {
		maxLevel = level;
	}

	/**
	 * Sets the maximum number of buildings of this type allowed
	 * @param level the level of the village hall
	 */
	abstract void setMaxCount(int level);
}

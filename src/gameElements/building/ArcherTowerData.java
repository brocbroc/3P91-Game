package gameElements.building;

/**
 * This class contains the data on a village's archer towers.
 * It holds the max level, count, and max count.
 */
public class ArcherTowerData extends BuildingData {
	private static final int[] maxCounts;

	static {
		maxCounts = new int[] { 5, 10, 15, 20, 25 };
	}

	/**
	 * Class constructor
	 */
	public ArcherTowerData() {
		super();
		maxCount = maxCounts[0];
	}

	/**
	 * Sets the maximum number of buildings of this type allowed
	 * @param level the level of the village hall
	 */
	@Override
	void setMaxCount(int level) {
		maxCount = maxCounts[level];
	}
}

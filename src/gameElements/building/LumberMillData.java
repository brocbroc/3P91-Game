package gameElements.building;

/**
 * This class contains the data on a village's lumber mills.
 * It holds the max level, count, and max count.
 */
public class LumberMillData extends BuildingData {
	private static final int[] maxCounts;

	static {
		maxCounts = new int[] { 2, 4, 6, 8, 10 };
	}

	/**
	 * Class constructor
	 */
	public LumberMillData() {
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

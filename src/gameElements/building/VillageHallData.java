package gameElements.building;

/**
 * This class contains the data on a village's village hall.
 * It holds the max level, count, and max count.
 */
public class VillageHallData extends BuildingData {
	public VillageHallData() {
		super();
		maxCount = 1;
	}

	/**
	 * Sets the maximum number of buildings of this type allowed
	 * @param level the level of the village hall
	 */
	@Override
	void setMaxCount(int level) {}
}

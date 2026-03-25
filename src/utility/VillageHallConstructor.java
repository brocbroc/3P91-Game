package utility;

import gameElements.BuildingType;
import gameElements.InhabitantType;
import gameElements.building.Building;
import gameElements.building.BuildingData;
import gameElements.building.VillageHall;
import gameElements.building.VillageHallData;
import gameElements.inhabitant.InhabitantData;

import java.util.EnumMap;

/**
 * This class represents a constructor for the <code>VillageHall</code> class.
 */
public class VillageHallConstructor implements BuildingConstructor {
	private EnumMap<BuildingType, BuildingData> buildingData;
	private EnumMap<InhabitantType, InhabitantData> inhabitantData;
	private VillageHallData data;

	public VillageHallConstructor(EnumMap<BuildingType, BuildingData> buildingData, EnumMap<InhabitantType, InhabitantData> inhabitantData) {
		this.buildingData = buildingData;
		this.inhabitantData = inhabitantData;
		data = (VillageHallData) buildingData.get(BuildingType.VILLAGE_HALL);
	}

	/**
	 * Returns the number of village halls.
	 * @return the number of village halls
	 */
	@Override
	public int getCount() {
		return data.getCount();
	}

	/**
	 * Returns the maximum number of village halls.
	 * @return the maximum number of village halls
	 */
	@Override
	public int getMaxCount() {
		return data.getMaxCount();
	}

	/**
	 * Returns the build cost
	 * @return the build cost
	 */
	@Override
	public Cost getBuildCost() {
		return VillageHall.getBuildCost();
	}

	/**
	 * Returns the build time
	 * @return the build time, in seconds
	 */
	@Override
	public int getBuildTime() {
		return VillageHall.getBuildTime();
	}

	/**
	 * Creates a new village hall at the given position.
	 * @param pos the position of the new village hall
	 * @return the new village hall
	 */
	@Override
	public Building addBuilding(Position pos) {
		return new VillageHall(pos, data, buildingData, inhabitantData);
	}
}

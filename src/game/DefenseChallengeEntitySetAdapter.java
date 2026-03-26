package game;

import ChallengeDecision.*;
import gameElements.building.Building;
import gameElements.building.Defense;
import java.util.List;

/**
 * This class uses an object adapter pattern to create a ChallengeEntitySet representing the
 * challengee.
 */
public class DefenseChallengeEntitySetAdapter extends ChallengeEntitySet<Double, Double> {
	/**
	 * Class constructor.
	 * @param buildings the buildings of the village
	 * @param inventory the village's inventory
	 */
	public DefenseChallengeEntitySetAdapter(Building[][] buildings, Inventory inventory) {
		super();
		List<ChallengeDefense<Double, Double>> entityDefenseList = this.getEntityDefenseList();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < buildings[i].length; j++) {
				entityDefenseList.add(
					new ChallengeDefense<>(0.0, (double) buildings[i][j].getHitPoints()));
			}
		}

		for (int i = 5; i < 7; i++) {
			for (int j = 0; j < buildings[i].length; j++) {
				entityDefenseList.add(
					new ChallengeDefense<>(
						(double) ((Defense) buildings[i][j]).getDamage(),
						(double) buildings[i][j].getHitPoints()
					)
				);
			}
		}

		List<ChallengeResource<Double, Double>> entityResourceList = this.getEntityResourceList();
		entityResourceList.add(new ChallengeResource<>((double) inventory.getGold()));
		entityResourceList.add(new ChallengeResource<>((double) inventory.getIron()));
		entityResourceList.add(new ChallengeResource<>((double) inventory.getLumber()));
	}
}

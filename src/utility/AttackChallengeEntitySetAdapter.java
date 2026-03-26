package utility;

import ChallengeDecision.ChallengeAttack;
import ChallengeDecision.ChallengeEntitySet;
import ChallengeDecision.ChallengeResource;
import game.Inventory;
import gameElements.InhabitantType;
import gameElements.inhabitant.*;
import java.util.EnumMap;
import java.util.List;

/**
 * This class uses an object adapter pattern to create a ChallengeEntitySet representing the
 * challenger.
 */
public class AttackChallengeEntitySetAdapter extends ChallengeEntitySet<Double, Double> {
	/**
	 * Class constructor
	 * @param attackForce the counts of the fighters
	 * @param inhabitantData the inhabitant data for all types
	 * @param inventory the village's inventory
	 */
	public AttackChallengeEntitySetAdapter(
		int[] attackForce,
		EnumMap<InhabitantType, InhabitantData> inhabitantData,
		Inventory inventory) {
		super();
		List<ChallengeAttack<Double, Double>> entityAttackList = this.getEntityAttackList();

		for (int i = 0; i < attackForce[0]; i++) {
			SoldierData data = (SoldierData) inhabitantData.get(InhabitantType.SOLDIER);
			entityAttackList.add(
				new ChallengeAttack<>((double) data.getDamage(), (double) data.getHitPoints()));
		}

		for (int i = 0; i < attackForce[1]; i++) {
			ArcherData data = (ArcherData) inhabitantData.get(InhabitantType.ARCHER);
			entityAttackList.add(
				new ChallengeAttack<>((double) data.getDamage(), (double) data.getHitPoints()));
		}

		for (int i = 0; i < attackForce[2]; i++) {
			KnightData data = (KnightData) inhabitantData.get(InhabitantType.KNIGHT);
			entityAttackList.add(
				new ChallengeAttack<>((double) data.getDamage(), (double) data.getHitPoints()));
		}

		for (int i = 0; i < attackForce[3]; i++) {
			CatapultData data = (CatapultData) inhabitantData.get(InhabitantType.CATAPULT);
			entityAttackList.add(
				new ChallengeAttack<>((double) data.getDamage(), (double) data.getHitPoints()));
		}

		List<ChallengeResource<Double, Double>> entityResourceList = this.getEntityResourceList();
		entityResourceList.add(new ChallengeResource<>((double) inventory.getGold()));
		entityResourceList.add(new ChallengeResource<>((double) inventory.getIron()));
		entityResourceList.add(new ChallengeResource<>((double) inventory.getLumber()));
	}
}

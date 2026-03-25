package gameElements.inhabitant;

/**
 * This interface specifies methods needed in the data for fighter classes.
 */
public interface FighterData {
	/**
	 * Returns the total hit points
	 * @return the total hit points
	 */
	int getHitPoints();

	/**
	 * Returns the damage done
	 * @return the damage done
	 */
	int getDamage();

	/**
	 * Returns the range of attack
	 * @return the range
	 */
	int getRange();
}

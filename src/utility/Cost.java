package utility;

/**
 * This class represents a cost in resources.
 * DO NOT TOUCH
 */
public class Cost {
	public final int GOLD;
	public final int IRON;
	public final int LUMBER;

	/**
	 * Class constructor.
	 * @param gold the amount of gold
	 * @param iron the amount of iron
	 * @param lumber the amount of lumber
	 */
	public Cost(int gold, int iron, int lumber) {
		this.GOLD = gold;
		this.IRON = iron;
		this.LUMBER = lumber;
	}
}

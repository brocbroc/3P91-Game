package game;

/**
 * This class represents a player of the game.
 * Every player has a village and an ID. The village is not deleted when the player is.
 */
public class Player {
	private final int ID;
	private final Village VILLAGE;

	/**
	 * Class constructor.
	 * @param id the player ID
	 * @param village the player's village
	 */
	public Player(int id, Village village) {
		ID = id;
		VILLAGE = village;
	}

	/**
	 * Returns the player ID.
	 * @return the player ID
	 */
	public int getId() { return ID; }

	/**
	 * Returns the player's village.
	 * @return the player's village
	 */
	public Village getVillage() { return VILLAGE; }
}

package game;

/**
 * This class represents a player of the game.
 */
public class Player {
	private int id;
	private Village village;

	/**
	 * This is a constructor for Player.
	 * @param id the player id
	 * @param village the player's village
	 */
	public Player(int id, Village village) {
		this.id = id;
		this.village = village;
	}

	/**
	 * This method returns the player id.
	 * @return the player id
	 */
	public int getId() { return id; }

	/**
	 * This method returns the player's village.
	 * @return the player's village
	 */
	public Village getVillage() { return village; }
}

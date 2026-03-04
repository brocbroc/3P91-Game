package gui;

import game.*;

/**
 * This class represents a graphical interface of a player.
 */
public class GraphicalInterface {
	private Player player;
	private Village base;

	/**
	 * Class constructor.
	 * @param player the player that owns the GUI
	 */
	public GraphicalInterface(Player player) {
		this.player = player;
		base = player.getVillage();
	}
}

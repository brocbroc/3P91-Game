package gui;

import game.*;

public class GraphicalInterface {
	private Player player;
	private Village base;

	public GraphicalInterface(Player player) {
		this.player = player;
		base = player.getVillage();
	}
}

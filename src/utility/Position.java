package utility;

import game.Village;

public class Position {
	public int x;
	public int y;

	public Position(int x, int y) {
		if (x < 0 || x >= Village.MAP_ROW_COUNT) {
			throw new IllegalArgumentException("x is out of bounds");
		}
		if (y < 0 || y >= Village.MAP_COL_COUNT) {
			throw new IllegalArgumentException("y is out of bounds");
		}

		this.x = x;
		this.y = y;
	}
}

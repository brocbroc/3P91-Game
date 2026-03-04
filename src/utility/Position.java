package utility;

import game.Village;

public class Position {
	public int x;
	public int y;

	public Position(int x, int y) {
		if (x < 0 || x >= Village.getMapRowCount()) {
			throw new IllegalArgumentException("x is out of bounds");
		}
		if (y < 0 || y >= Village.getMapColCount()) {
			throw new IllegalArgumentException("y is out of bounds");
		}

		this.x = x;
		this.y = y;
	}
}

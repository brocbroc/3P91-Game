package utility;

import game.Village;

/**
 * This class represents a position on the map.
 * DO NOT TOUCH
 */
public class Position {
	public final int X;
	public final int Y;

	/**
	 * Class constructor.
	 * @param x the row
	 * @param y the column
	 */
	public Position(int x, int y) {
		if (x < 0 || x >= Village.getMapRowCount()) {
			throw new IllegalArgumentException("x is out of bounds");
		}
		if (y < 0 || y >= Village.getMapColCount()) {
			throw new IllegalArgumentException("y is out of bounds");
		}

		this.X = x;
		this.Y = y;
	}
}

package gameElements;

import utility.*;

/**
 * This class represents an inhabitant of a village.
 */
public abstract class Inhabitant {
	protected Position position;
	protected Position newPosition;
	protected int level;

	public Inhabitant() {
		level = 1;
	}

	public int getLevel() {
		return level;
	}

	public void upgrade() {
		level++;
	}

	public Position getPosition() {
		return position;
	}
}
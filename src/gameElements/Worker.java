package gameElements;

import utility.Position;

public class Worker extends Inhabitant {
	private boolean isBusy;

	public Worker() {
		super();
		position = new Position(0, 0); // Spawn point
		isBusy = false;
	}

	public boolean isBusy() { return isBusy; }

	public void setBusy(boolean busy) { isBusy = busy; }
}

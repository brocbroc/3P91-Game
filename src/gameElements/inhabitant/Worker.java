package gameElements.inhabitant;

import utility.Cost;

/**
 * This class represents a worker. Workers are responsible for construction.
 */
public class Worker extends Inhabitant implements Peasant {
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public Worker() {
		super();
		isBusy = false;
	}

	/**
	 * Checks if the worker is busy.
	 * @return <code>true</code> if the worker is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() {
		return isBusy;
	}

	/**
	 * Sets whether or not the worker is busy.
	 * @param isBusy the state of the worker
	 */
	@Override
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
}

package gameElements.inhabitant;

/**
 * This class represents a lumberman. They can produce lumber at a lumber mill.
 */
public class Lumberman extends Inhabitant implements Peasant {
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public Lumberman() {
		super();
		isBusy = false;
	}

	/**
	 * Checks if the lumberman is busy.
	 * @return <code>true</code> if the lumberman is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() {
		return isBusy;
	}

	/**
	 * Sets whether or not the lumberman is busy.
	 * @param isBusy the state of the lumberman
	 */
	@Override
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
}

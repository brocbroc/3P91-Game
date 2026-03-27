package gameElements.inhabitant;

/**
 * This class represents a gold miner. They produce gold at gold mines.
 */
public class GoldMiner extends Inhabitant implements Peasant {
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public GoldMiner() {
		super();
		isBusy = false;
	}

	/**
	 * Checks if the gold miner is busy.
	 * @return <code>true</code> if the gold miner is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() {
		return isBusy;
	}

	/**
	 * Sets whether or not the gold miner is busy.
	 * @param isBusy the state of the gold miner
	 */
	@Override
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
}

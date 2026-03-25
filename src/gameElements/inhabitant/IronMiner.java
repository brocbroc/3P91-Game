package gameElements.inhabitant;

import utility.Cost;

/**
 * This class represents an iron miner. They produce iron at iron mines.
 */
public class IronMiner extends Inhabitant implements Peasant {
	private boolean isBusy;

	/**
	 * Class constructor.
	 */
	public IronMiner() {
		super();
		isBusy = false;
	}

	/**
	 * Checks if the iron miner is busy.
	 * @return <code>true</code> if the iron miner is busy, <code>false</code> if not busy
	 */
	@Override
	public boolean isBusy() {
		return isBusy;
	}

	/**
	 * Sets whether or not the iron miner is busy.
	 * @param isBusy the state of the iron miner
	 */
	@Override
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
}

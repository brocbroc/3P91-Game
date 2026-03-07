package gameElements.inhabitant;

/**
 * This interface represents the inhabitants that are not part of the army.
 * Peasants include workers, miners, and lumbermen.
 */
public interface Peasant {
	/**
	 * Checks if the peasant is busy.
	 * @return <code>true</code> if the peasant is busy, <code>false</code> if not busy
	 */
	boolean isBusy();

	/**
	 * Sets whether or not the peasant is busy.
	 * @param isBusy the state of the peasant
	 */
	void setBusy(boolean isBusy);
}

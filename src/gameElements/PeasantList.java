package gameElements;

import gameElements.inhabitant.Peasant;
import java.util.List;
import java.util.ArrayList;

/**
 * This class stores a list of peasants.
 * The peasants are split into free peasants and busy peasants.
 * @param <T> the type of peasant
 */
public class PeasantList<T extends Peasant> {
	private List<T> freePeasants;
	private List<T> busyPeasants;
	private int count;

	/**
	 * Class constructor.
	 */
	public PeasantList() {
		freePeasants = new ArrayList<>();
		busyPeasants = new ArrayList<>();
		count = 0;
	}

	/**
	 * Checks if there are no free peasants.
	 * @return <code>true</code> if no peasants are free, <code>false</code> if there is a free peasant
	 */
	public boolean isFreePeasantEmpty() { return freePeasants.isEmpty(); }

	/**
	 * Returns the total number of peasants
	 * @return the number of peasants
	 */
	public int getCount() { return count; }

	/**
	 * Gets a free peasant, sets them to busy, and returns the peasant.
	 * @return a peasant
	 */
	public T getPeasant() {
		T peasant = freePeasants.remove(0);
		peasant.setBusy(true);
		busyPeasants.add(peasant);
		return peasant;
	}

	/**
	 * Receives a busy peasant and sets them to free.
	 * @param peasant a busy peasant
	 */
	public void freePeasant(T peasant) {
		peasant.setBusy(false);
		busyPeasants.remove(peasant);
		freePeasants.add(peasant);
	}

	/**
	 * Adds a new peasant
	 * @param peasant a free peasant
	 */
	public void addPeasant(T peasant) {
		freePeasants.add(peasant);
		count++;
	}
}

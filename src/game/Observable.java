package game;

/**
 * This interface defines an observable subject.
 */
public interface Observable {
	/**
	 * Adds an observer
	 * @param o an observer
	 */
	void addObserver(Observer o);

	/**
	 * Removes an observer
	 * @param o an observer
	 */
	void removeObserver(Observer o);

	/**
	 * Notifies observers
	 * @param message a string describing the changes to the subject
	 */
	void notifyObservers(String message);
}

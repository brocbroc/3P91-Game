package game;

/**
 * This interface defines an observer.
 */
public interface Observer {
	/**
	 * Update the observer
	 * @param message a string describing the changes to the observable subject
	 */
	void update(String message);
}

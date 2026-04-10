package server;

import client.VillageSnapshot;
import java.io.Serializable;

/**
 * This class is used for transferring information between server and client.
 */
public class Packet implements Serializable {
	private Protocol header;
	private String message;
	private VillageSnapshot snapshot;

	/**
	 * Class constructor.
	 * @param header the command
	 * @param message the message sent
	 */
	public Packet(Protocol header, String message) {
		this.header = header;
		this.message = message;
		snapshot = null;
	}

	/**
	 * Class constructor.
	 * @param header the command
	 * @param message the message sent
	 * @param snapshot a snapshot of the village
	 */
	public Packet(Protocol header, String message, VillageSnapshot snapshot) {
		this.header = header;
		this.message = message;
		this.snapshot = snapshot;
	}

	/**
	 * Returns the protocol command
	 * @return the protocol command
	 */
	public Protocol getHeader() {
		return header;
	}

	/**
	 * Returns the packet content
	 * @return the packet content
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the village snapshot
	 * @return the village snapshot
	 */
	public VillageSnapshot getSnapshot() {
		return snapshot;
	}
}

package server;

/**
 * This enum represents the list of valid protocol commands.
 */
public enum Protocol {
	KEY, // Verify client, handshake protocol
	UPDATE, // Client handler updates client data
	ALERT, // Client handler sends message to client
	ADD_BUILDING,
	UPGRADE_BUILDING,
	TRAIN_INHABITANT,
	UPGRADE_INHABITANT,
	GENERATE_VILLAGE,
	PROMPT,
	ATTACK,
	ATTACK_TESTING,
	VILLAGE_TESTING,
	CHECK_RANK,
	CLOSE_GAME,
	EXIT_PROGRAM
}

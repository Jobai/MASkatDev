package de.skat3.network.datatypes;

public enum MessageType {
    
	/**
	 * A Message FROM the server TO the user to do something
	 */
    COMMAND_ACTION,
    /**
	 * A Message FROM the server TO the user to inform him of something
	 */
    COMMAND_INFO,
    /**
	 * A Message FROM the user TO the server to inform him of something (answer a COMMAND_ACTION)
	 */
    ANWSER_ACTION,
    /**
     * protocol message to inform the server about the new connections / client details
     */
    CONNECTION_OPEN,
    /**
     * protocol message to inform the server that the client will terminate the connection
     */
    CONNECTION_CLOSE,
    /**
     * a simple chat message 
     */
    CHAT_MESSAGE,
    /**
     * Mulitcast message to ask for a lobby on the network
     */
    LOBBY_QUESTION,
    /**
     * Mulitcast message to answer a LOBBY_QUESTION
     */
    LOBBY_ANSWER,
    /**
     * Message to join a lobby (includes the password)
     */
	LOBBY_JOIN,
	/**
	 * Message to indicate that he cannot join the lobby (Lobby full or wrong password)
	 */
	LOBBY_DENIED,
	
	STATE_CHANGE;
  
}

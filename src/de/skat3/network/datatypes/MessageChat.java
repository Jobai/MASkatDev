package de.skat3.network.datatypes;

/**
 * @author Jonas Bauer 
 * 
 * 
 *
 */
public class MessageChat extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message;
	public String nick;
	boolean profanity; // true if it includes profanity [additional feature] //TODO
	boolean command; // true if this is not a chat message but a command for the server;

	public MessageChat(String message, String nick) {
		super(MessageType.MESSAGE_CHAT);
		this.message = message;
		this.nick = nick;
		this.profanity = false;
		this.command = false;
		
	}

}

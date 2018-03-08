package de.skat3.network.datatypes;

/**
 * @author Jonas Bauer 
 * 
 * 
 *
 */
public class MessageControl extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object gameState;
	boolean serverIssued; 
	

	public MessageChat(MessageType mt, String sender, String receiver, Object gameState) {
		super(mt, sender, receiver);
		this.serverIssued = false;
		
	}

}

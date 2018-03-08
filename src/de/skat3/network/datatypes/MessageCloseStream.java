package de.skat3.network.datatypes;

/**
 * @author Jonas Bauer 
 * Message Klasse die Benutzt wird um dem Sever mitzuteilen das der Client beendet wurde
 * und die Verbindung geschlossen werden kann
 * 
 *
 */
public class MessageCloseStream extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageCloseStream() {
		super(MessageType.CLOSE_CONNECTION);
		// TODO Auto-generated constructor stub
	}

}

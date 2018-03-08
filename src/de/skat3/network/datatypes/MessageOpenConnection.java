package de.skat3.network.datatypes;

/**
 * Klasse die verwendet wird um den Server anzuweisen eine Frage zu übersenden
 * 
 * 
 *
 */
public class MessageOpenConnection extends Message {
	private static final long serialVersionUID = 1L;
	
	
	public MessageOpenConnection(){
		super(MessageType.OPEN_CONNECTION);
		
	}

}

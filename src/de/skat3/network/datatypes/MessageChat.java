package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * Network message class for the marshaling and sending of chat messages to the sever and back to
 * the clients.
 * 
 * @author Jonas Bauer
 *
 */
public class MessageChat extends Message implements Serializable {

  private static final long serialVersionUID = 4675831431860851912L;
  public String message;
  public String nick;

  /**
   * Default constructor for the creation of a MessageChat instance.
   * 
   * @author Jonas Bauer
   * @param message the chat message to be transmitted.
   * @param nick username of the sender.
   */
  public MessageChat(String message, String nick) {
    super(MessageType.CHAT_MESSAGE);
    this.message = message;
    this.nick = nick;
   

  }

}

package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageChat extends Message implements Serializable {
  public String message;
  public String nick;
  @Deprecated
  private boolean command; // true if this is not a chat message but a command for the server;

  public MessageChat(String message, String nick) {
    super(MessageType.CHAT_MESSAGE);
    this.message = message;
    this.nick = nick;
    this.command = false;

  }

}

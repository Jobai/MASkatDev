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
  private boolean profanity; // true if it includes profanity [additional feature] //TODO
  private boolean command; // true if this is not a chat message but a command for the server;

  public MessageChat(String message, String nick) {
    super(MessageType.CHAT_MESSAGE);
    this.message = message;
    this.nick = nick;
    this.setProfanity(false);
    this.setCommand(false);

  }

  /**
   * @author Jonas Bauer
   * @return the command
   */
  public boolean isCommand() {
    return command;
  }

  /**
   * @author Jonas Bauer
   * @param command the command to set
   */
  public void setCommand(boolean command) {
    this.command = command;
  }

  /**
   * @author Jonas Bauer
   * @return the profanity
   */
  public boolean isProfanity() {
    return profanity;
  }

  /**
   * @author Jonas Bauer
   * @param profanity the profanity to set
   */
  public void setProfanity(boolean profanity) {
    this.profanity = profanity;
  }

}

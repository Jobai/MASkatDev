package de.skat3.network.datatypes;

/**
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageCommand extends Message {

  public MessageCommand(MessageType messageType, String receiver, CommandType commandType) {
    super(messageType, "HOST", receiver);
    this.ct = commandType;
    // TODO Auto-generated constructor stub
  }


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public Object gameState;
  CommandType ct;

}

package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageCommand extends Message implements Serializable {

  public MessageCommand(MessageType messageType, String receiver, CommandType commandType) {
    super(messageType, "HOST", receiver);
    this.ct = commandType;
    super.setSubType(commandType);
    // TODO Auto-generated constructor stub
  }


  public MessageCommand(MessageType stateChange, String string) {
    // TODO Auto-generated constructor stub
    super(stateChange, "HOST", string);
//    this.ct = commandType;
  }


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public Object gameState;
  CommandType ct;

}

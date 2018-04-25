package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageCommand extends Message implements Serializable {

  public Object gameState;
  CommandType ct;


  public MessageCommand(MessageType messageType, String receiver, CommandType commandType) {
    super(messageType, "HOST", receiver);
    this.ct = commandType;
    super.setSubType(commandType);
  }


  @Deprecated
  public MessageCommand(MessageType stateChange, String string) {
    super(stateChange, "HOST", string);
  }


}

package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * Network message class for the marshaling and sending of Commands from the server to the
 * client. This class extends the Message class.
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageCommand extends Message implements Serializable {


  private static final long serialVersionUID = -9101668144405453072L;
  public Object gameState;
  CommandType ct;


  /**
   * Default constructor for the creation of a MessageCommand instance. 
   * Automatically sets the sender to the server;
   * @author Jonas Bauer
   * @param messageType type of the message;
   * @param receiver the user who should receive the command;
   * @param commandType Type of the command;
   */
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

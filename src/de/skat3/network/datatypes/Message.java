/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0
 * 07.03.2018
 * 
 * (c) 2018 All Rights Reserved. 
 * -------------------------
 */

package de.skat3.network.datatypes;

import de.skat3.gamelogic.Player;
import de.skat3.network.datatypes.SubType;
import java.io.Serializable;


/**
 * @author Jonas Bauer
 *
 */
public abstract class Message implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3809573340211560453L;
  private MessageType messageType;
  private String sender;
  private String receiver;
  public Player originSender;
  public SubType subType;
  public Object payload;
  public Object secondPayload;
  
  /**
   * @author Jonas Bauer
   * @param messageType
   * @param sender
   * @param receiver
   */
  public Message(MessageType messageType, String sender, String receiver) {
    this.messageType = messageType;
    this.sender = sender;
    this.receiver = receiver;
   
  }

  public Message(MessageType messageType) {
    // TODO Auto-generated constructor stub
    this.messageType = messageType;
  }

  public MessageType getType() {
    // TODO Auto-generated method stub
    return messageType;
  }

  public SubType getSubType() {
    // TODO Auto-generated method stub
    return subType;
  }

  public void setSubType(SubType st) {
    // TODO Auto-generated method stub
    this.subType = st;
  }
  
  

}

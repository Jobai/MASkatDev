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
  
  

}

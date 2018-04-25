/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 07.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
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
    this.setSender(sender);
    this.setReceiver(receiver);

  }

  public Message(MessageType messageType) {
    this.messageType = messageType;
  }

  public MessageType getType() {
    return messageType;
  }

  public SubType getSubType() {
    return subType;
  }

  public void setSubType(SubType st) {
    this.subType = st;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }



}

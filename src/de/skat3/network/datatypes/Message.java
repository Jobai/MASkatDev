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
 * Underlying message class for the marshaling of transmitted information and commands over the
 * network.
 * 
 * @author Jonas Bauer
 *
 */
public abstract class Message implements Serializable {


  private static final long serialVersionUID = -2464570403601180110L;
  private MessageType messageType;
  @Deprecated
  private String sender;
  @Deprecated
  private String receiver;
  public Player originSender;
  public SubType subType;
  public Object payload;
  public Object secondPayload;

  /**
   * broad constructor for messages. Sets many information. Used rarely.
   * 
   * @author Jonas Bauer
   * @param messageType MessageType of the message
   * @param sender Name of the sending player
   * @param receiver Name of the receiving player
   * 
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

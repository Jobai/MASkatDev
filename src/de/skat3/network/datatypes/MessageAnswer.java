package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * Network message class for the marshaling and sending of answers from the client back to the
 * server. This class extends the Message class.
 * 
 * @author Jonas Bauer
 *
 */
public class MessageAnswer extends Message implements Serializable {

  /**
   * Default constructor for the MessageAnswer class. Automatically sets the target as the server.
   * 
   * @author Jonas Bauer
   * @param sender Name of the sending player.
   * @param answerType Type of answer.
   */
  public MessageAnswer(String sender, AnswerType answerType) {
    super(MessageType.ANWSER_ACTION, sender, "HOST");
    this.at = answerType; // XXX
    super.setSubType(answerType);
  }


  private static final long serialVersionUID = 1L;
  @Deprecated
  public Object gameState;
  @Deprecated
  public Object additionalPlayload;
  AnswerType at;

}

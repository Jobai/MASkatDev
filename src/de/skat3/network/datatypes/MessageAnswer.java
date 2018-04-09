package de.skat3.network.datatypes;

import java.io.Serializable;

/**
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageAnswer extends Message implements Serializable{

  public MessageAnswer(String sender, AnswerType answerType) {
    super(MessageType.ANWSER_ACTION, sender, "HOST");
    this.at = answerType;
    super.setSubType(answerType);
    // TODO Auto-generated constructor stub
  }


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Deprecated
  public Object gameState;
  public Object additionalPlayload;
  AnswerType at;

}

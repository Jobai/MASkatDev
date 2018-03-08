package de.skat3.network.datatypes;

/**
 * @author Jonas Bauer
 * 
 * 
 *
 */
public class MessageAnswer extends Message {

  public MessageAnswer(String sender, AnswerType answerType) {
    super(MessageType.ANWSER_ACTION, sender, "HOST");
    this.at = answerType;
    // TODO Auto-generated constructor stub
  }


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public Object gameState;
  AnswerType at;

}

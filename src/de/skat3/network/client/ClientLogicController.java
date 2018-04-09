/**
 * 
 */
package de.skat3.network.client;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.network.datatypes.AnswerType;
import de.skat3.network.datatypes.MessageAnswer;

/**
 * @author Jonas
 * GUI > MainController > this class > ClientNetwork
 * 
 * I AM CALLED BY THE GUI / MAIN CONTROLLER
 * 
 * I implement [SEND] AnswerTypes
 */
public class ClientLogicController {

  GameClient gc;
	/**
	 * 
	 */
	public ClientLogicController() {
		// TODO Auto-generated constructor stub
	}

  public ClientLogicController(GameClient gameClient) {
    // TODO Auto-generated constructor stub
    this.gc = gameClient;
  }
  
  
  public void bidAnswer(boolean answer){
    
    MessageAnswer ma = new MessageAnswer("ME", AnswerType.BID_ANSWER);
    ma.payload = answer;
    gc.sendToServer(ma);
    
  }
  
  public void playAnswer(Card c){
    MessageAnswer ma = new MessageAnswer("ME", AnswerType.PLAY_ANSWER);
    ma.payload = c;
    gc.sendToServer(ma);
  }
  
  public void handAnswer(boolean hand){
    MessageAnswer ma = new MessageAnswer("ME", AnswerType.HAND_ANSWER);
    ma.payload = hand;
    gc.sendToServer(ma);
  }
  
  public void throwAnswer(Card c1, Card c2){
    MessageAnswer ma = new MessageAnswer("ME", AnswerType.THROW_ANSWER);
    Card[] skat =  {c1,c2};
    ma.payload = skat;
//    ma.additionalPlayload = hand
    gc.sendToServer(ma);
  }
  
  public void contractAnswer(Contract con){
    MessageAnswer ma = new MessageAnswer("ME", AnswerType.CONTRACT_ANSWER);
    ma.payload = con;
    gc.sendToServer(ma);
  }
  

}

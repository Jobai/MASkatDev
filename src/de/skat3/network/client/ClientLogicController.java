/**
 * 
 */

package de.skat3.network.client;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.AnswerType;
import de.skat3.network.datatypes.MessageAnswer;
import de.skat3.network.datatypes.MessageChat;
import de.skat3.network.datatypes.MessageCommand;
import de.skat3.network.datatypes.MessageConnection;
import de.skat3.network.datatypes.MessageType;

/**
 * @author Jonas
 * 
 *         GUI > MainController > this class > ClientNetwork
 * 
 *         I AM CALLED BY THE GUI / MAIN CONTROLLER
 * 
 *         I implement [SEND] AnswerTypes
 */
public class ClientLogicController {

  GameClient gc;
  String userName;


  public ClientLogicController(GameClient gameClient) {
    this.gc = gameClient;
    userName = SkatMain.ioController.getLastUsedProfile().getName();
  }


  public void bidAnswer(boolean answer) {
    System.out.println("BID ANSWER SEND  - " + answer);
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.BID_ANSWER);
    ma.payload = answer;
    gc.sendToServer(ma);

  }

  public void playAnswer(Card c) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.PLAY_ANSWER);
    ma.payload = c;
    gc.sendToServer(ma);
  }

  public void handAnswer(boolean hand) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.HAND_ANSWER);
    ma.payload = hand;
    gc.sendToServer(ma);
  }

  public void throwAnswer(Hand hand, Card[] skat) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.THROW_ANSWER);
    ma.payload = skat;
    ma.additionalPlayload = hand;
    gc.sendToServer(ma);
  }

  public void contractAnswer(Contract con, AdditionalMultipliers am) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.CONTRACT_ANSWER);
    ma.payload = con;
    ma.additionalPlayload = am;
    gc.sendToServer(ma);
  }

  public void sendChatMessage(String chatString) {
    MessageChat mc = new MessageChat(chatString, userName);
    gc.sendToServer(mc);

  }

  public void leaveGame() {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_CLOSE);
    gc.sendToServer(mc);
//    try {
//      Thread.sleep(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    gc.closedByClient = true;
    gc.closeConnection();
  }

  public void kontraAnswer() {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.KONTRA_ANSWER);
    gc.sendToServer(ma);
  }

  public void reKontraAnswer() {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.REKONTRA_ANSWER);
    gc.sendToServer(ma);
  }


  public void announceGameStarted() {
    System.out.println("START GAME SEND OUT");
    MessageCommand mc = new MessageCommand(MessageType.STATE_CHANGE, "ALL", null);
    mc.payload = "START"; // XXX
    gc.sendToServer(mc);

  }

  public void disconnectFromLobby() {
    MessageCommand mc = new MessageCommand(MessageType.CONNECTION_CLOSE, "SERVER", null);
    gc.sendToServer(mc);
    try {
      this.wait(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    gc.closeConnection();



  }

}

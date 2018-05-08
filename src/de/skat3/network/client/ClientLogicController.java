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
import java.util.logging.Logger;


/**
 * Handles all calls by the GUI and sends them to the netwokr (using the GameClient).
 * 
 * @author Jonas Bauer
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
  Logger logger = Logger.getLogger("de.skat3.network.client");


  /**
   * 
   * @author Jonas Bauer
   * @param gameClient
   */
  public ClientLogicController(GameClient gameClient) {
    this.gc = gameClient;
    try {
      userName = SkatMain.ioController.getLastUsedProfile().getName();
    } catch (NullPointerException e) {
      // silent for JUNIT testing purposes.
    }

  }

  /**
   * Sends the bid answer of the user to the network.
   * 
   * @author Jonas Bauer
   * @param answer value if he accepts the current bid.
   */
  public void bidAnswer(boolean answer) {
    logger.fine("BID ANSWER SEND  - " + answer);
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.BID_ANSWER);
    ma.payload = answer;
    gc.sendToServer(ma);

  }

  /**
   * Sends the played card to the network.
   * 
   * @author Jonas Bauer
   * @param card the played card
   */
  public void playAnswer(Card card) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.PLAY_ANSWER);
    ma.payload = card;
    gc.sendToServer(ma);
  }

  /**
   * Sends the answer whether the player wants to play a handgame.
   * 
   * @author Jonas Bauer
   * @param handgame if he wants to play a handgame
   */
  public void handAnswer(boolean handgame) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.HAND_ANSWER);
    ma.payload = handgame;
    gc.sendToServer(ma);
  }

  /**
   * Sends the answer what cards the player discards into the skat (if he plays a handgame).
   * 
   * @author Jonas Bauer
   * @param hand the current hand of the player.
   * @param skat the discarded cards (the skat).
   */
  public void throwAnswer(Hand hand, Card[] skat) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.THROW_ANSWER);
    ma.payload = skat;
    ma.additionalPlayload = hand;
    gc.sendToServer(ma);
  }

  /**
   * Sends the chosen contract and additional multipliers.
   * 
   * @author Jonas Bauer
   * @param contract the contract (suit, null or grand)
   * @param am the additional multipliers (schneider, schwarz, ouvert)
   */
  public void contractAnswer(Contract contract, AdditionalMultipliers am) {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.CONTRACT_ANSWER);
    ma.payload = contract;
    ma.additionalPlayload = am;
    gc.sendToServer(ma);
  }

  /**
   * Sends a chat message to all players.
   * 
   * @author Jonas Bauer
   * @param chatString the chat message.
   */
  public void sendChatMessage(String chatString) {
    MessageChat mc = new MessageChat(chatString, userName);
    gc.sendToServer(mc);

  }

  /**
   * Leave the game and send a message to inform the server and all other players.
   * 
   * @author Jonas Bauer
   */
  public void leaveGame() {
    MessageConnection mc = new MessageConnection(MessageType.CONNECTION_CLOSE);
    gc.sendToServer(mc);
    gc.closedByClient = true;
    gc.closeConnection();
  }

  /**
   * Send a kontra announcement to the server.
   * 
   * @author Jonas Bauer
   */
  public void kontraAnswer() {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.KONTRA_ANSWER);
    gc.sendToServer(ma);
  }

  /**
   * Send a rekontra announcement to the server.
   * 
   * @author Jonas Bauer
   */
  public void reKontraAnswer() {
    MessageAnswer ma = new MessageAnswer(userName, AnswerType.REKONTRA_ANSWER);
    gc.sendToServer(ma);
  }


  /**
   * Starts the game for all players and sends out a STATE_CHANGE with game started.
   * 
   * @author Jonas Bauer
   */
  public void announceGameStarted() {
    logger.fine("START GAME SEND OUT");
    MessageCommand mc = new MessageCommand(MessageType.STATE_CHANGE, "ALL", null);
    mc.payload = "START";
    gc.sendToServer(mc);
  }

}

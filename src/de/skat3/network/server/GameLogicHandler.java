package de.skat3.network.server;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Hand;
import de.skat3.network.datatypes.AnswerType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageAnswer;

/**
 * Handles messages from the network and informs the logic of chages and answers. ServerNetwork >
 * thisClass > Logic IMPLEMENTs ANSWERTYPEs
 * 
 * @author Jonas
 *
 */
public class GameLogicHandler {

  GameController gc;

  public GameLogicHandler(GameController gc) {

    this.gc = gc;
  }

  /**
   * Handles all answer by the client and passes the message to the corresponding method.
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the clients
   */
  public void handleAnswer(Message m) {
    AnswerType at = (AnswerType) m.getSubType();
    switch (at) {
      case BID_ANSWER:
        bidHandler(m);
        break;
      case CONTRACT_ANSWER:
        contractHandler(m);
        break;
      case HAND_ANSWER:
        handHandler(m);
        break;
      case PLAY_ANSWER:
        playHandler(m);
        break;
      case THROW_ANSWER:
        skatHandler(m);
        break;
      case GAME_ANSWER:
        gameHandler(m);
        break;
      case MATCH_ANSWER:
        // do nothing - not used
        break;
      case ROUND_ANSWER:
        // do nothing - not used
        break;
      case KONTRA_ANSWER:
        kontraHandler(m);
        break;
      case REKONTRA_ANSWER:
        kontraHandler(m);
        break;
      default:
        throw new AssertionError();
    }
  }

  /**
   * Notify logic of the bid from the client.
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the client.
   */
  private void bidHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    boolean accept = (boolean) ma.payload;
    gc.notifyLogicofBid(accept);


  }

  /**
   * Notify the logic of the selected contract by the delcarer.
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the client.
   */
  private void contractHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    Contract con = (Contract) ma.payload;
    AdditionalMultipliers am = (AdditionalMultipliers) ma.additionalPlayload;
    gc.notifyLogicofContract(con, am);
  }

  /**
   * notify the logic of the handgame option.
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the client.
   */
  private void handHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    gc.notifyLogicOfHandGame((boolean) ma.payload);
  }

  /**
   * Notify the logic of a played card.
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the client.
   */
  private void playHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    Card c = (Card) ma.payload;
    gc.notifyLogicofPlayedCard(c);
  }

  /**
   * Notify the logic of the current skat (after the player changed it).
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the client.
   */
  private void skatHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    Card[] skat = (Card[]) ma.payload;
    Hand h = (Hand) ma.additionalPlayload;
    gc.notifyLogicOfNewSkat(h, skat);

  }

  private void gameHandler(Message m) {
    // do nothing - function not needed
  }

  /**
   * Notify the logic of an announced (re)kontra.
   * 
   * @author Jonas Bauer
   * @param m network message (answer) from the client.
   */
  private void kontraHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;

    if (ma.getSubType() == AnswerType.KONTRA_ANSWER) {
      gc.notifyLogicofKontra();
    } else if (ma.getSubType() == AnswerType.REKONTRA_ANSWER) {
      gc.notifyLogicofRekontra();
    }
  }

}

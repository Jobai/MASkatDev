package de.skat3.network.server;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.GameController;
import de.skat3.gamelogic.Hand;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.AnswerType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageAnswer;
import de.skat3.network.datatypes.SubType;

/**
 * 
 * ServerNetwork > thisClass > Logic
 * 
 * I IMPLEMENT ANSWERTYPEs
 * 
 * @author Jonas
 *
 */
public class GameLogicHandler {

  GameController gc;

  public GameLogicHandler(GameController gc) {

    this.gc = gc;
  }

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
        break;
      case ROUND_ANSWER:
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

  private void bidHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    boolean accept = (boolean) ma.payload;
    gc.notifyLogicofBid(accept);


  }

  private void contractHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    Contract con = (Contract) ma.payload;
    AdditionalMultipliers am = (AdditionalMultipliers) ma.additionalPlayload;
    gc.notifyLogicofContract(con, am);
  }

  private void handHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    gc.notifyLogicOfHandGame((boolean) ma.payload);
  }

  private void playHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    Card c = (Card) ma.payload;
    gc.notifyLogicofPlayedCard(c);
  }

  private void skatHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;
    Card[] skat = (Card[]) ma.payload;
    Hand h = (Hand) ma.additionalPlayload;
    gc.notifyLogicOfNewSkat(h, skat);

  }

  private void gameHandler(Message m) {
    // do nothing - function not needed
  }

  private void kontraHandler(Message m) {
    MessageAnswer ma = (MessageAnswer) m;

    if (ma.getSubType() == AnswerType.KONTRA_ANSWER) {
      gc.notifyLogicofKontra();
    } else if (ma.getSubType() == AnswerType.REKONTRA_ANSWER) {
      gc.notifyLogicofRekontra();
    }
  }

}

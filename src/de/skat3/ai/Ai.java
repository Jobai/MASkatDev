package de.skat3.ai;

import java.io.Serializable;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Position;

@SuppressWarnings("serial")
public abstract class Ai implements Serializable {



  /**
   * Called when the Ai has to decide if it accepts a bid.
   * 
   * @return true if Ai accepts bid, false if not.
   */
  public abstract boolean acceptBid(int bid);

  /**
   * Called when the Ai has to decide if it plays a handgame.
   * 
   * @return true if Ai accepts handgame, false if not.
   */
  public abstract boolean acceptHandGame();

  /**
   * Called when the Ai plays a handgame and has to set the contract.
   */
  public abstract AdditionalMultipliers chooseAdditionalMultipliers();

  /**
   * Ai sets the Contract if he is declarer.
   * 
   * @return
   */
  public abstract Contract chooseContract();


  /**
   * Ai plays a card.
   */
  public abstract Card chooseCard();

  /*
   * Ai darf folgende Methoden aufrufen: SkatMain.mainController.localKontraAnnounced();
   * SkatMain.mainController.localRekontraAnnounced();
   * 
   */

  /**
   * 
   * @param skat
   * @return Card[0-9] = hand Card[10-11] = skat
   */
  public abstract ReturnSkat selectSkat(Card[] skat);



  public abstract void setHand(Hand hand);
  
  public abstract Hand getHand();

  public abstract void setPosition(Position position);

  public abstract void setIsSolo(boolean isSolo);



}


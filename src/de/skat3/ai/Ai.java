package de.skat3.ai;

import java.io.Serializable;
import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Hand;
import de.skat3.gamelogic.Position;

/**
 * 
 * 
 * 
 * @author Kai Baumann, Artem Zamarajev
 *
 */
@SuppressWarnings("serial")
public abstract class Ai implements Serializable {

  /**
   * Decides if the bid will be accepted.
   * 
   * @param bid the bid that is to accept or reject.
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
   * Chooses additional multipliers. Called only if Ai plays a handgame.
   * 
   * @return additional multipliers that were chosen.
   */
  public abstract AdditionalMultipliers chooseAdditionalMultipliers();

  /**
   * Chooses a contract.
   * 
   * @return chosen contract.
   */
  public abstract Contract chooseContract();


  /**
   * Chooses a card to play.
   * 
   * @return the card to play.
   */
  public abstract Card chooseCard();

  /**
   * Makes a decision on selecting cards from skat.
   * 
   * @param skat represents the skat on the table.
   * @return an instance of ReturnSkat.
   * @see ReturnSkat.
   */
  public abstract ReturnSkat selectSkat(Card[] skat);

  public abstract void setHand(Hand hand);
  
  public abstract Hand getHand();

  public abstract void setPosition(Position position);

  public abstract void setIsSolo(boolean isSolo);



}


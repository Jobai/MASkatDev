package de.skat3.gamelogic;

/**
 * Interface for logic and network.
 * 
 * @author Kai Baumann
 *
 */
public interface GameLogicInterface {


  /**
   * Called by the server to notify the logic of a locally played card.
   */
  public void notifyLogicofPlayedCard(Card card);

  /**
   * Called by the server to notify the logic of a locally made bid.
   */
  public void notifyLogicofBid(boolean accepted);

  /**
   * Called by the server to notify the logic of a accepted or declined handgame.
   */

  public void notifyLogicOfHandGame(boolean accepted);

  /**
   * Called by the server to update the solo player hand and the skat.
   */

  public void notifyLogicOfNewSkat(Hand hand, Card[] skat);

  /**
   * Called by the server to notify the logic of a locally selected contract.
   */

  public void notifyLogicofContract(Contract contract, AdditionalMultipliers additionMultipliers);

  /**
   * Called by the server to notify the logic of an announced kontra.
   */

  public void notifyLogicofKontra();

  /**
   * Called by the server to notify the logic of an announced rekontra.
   */
  public void notifyLogicofRekontra();



}

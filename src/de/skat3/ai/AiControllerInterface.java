package de.skat3.ai;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;

public interface AiControllerInterface {

  /**
   * Called when the Ai has to decide if it accepts a bid.
   * 
   * @return true if Ai accepts bid, false if not.
   */
  public boolean acceptBid(int bid);

  /**
   * Called when the Ai has to decide if it plays a handgame.
   * 
   * @return true if Ai accepts handgame, false if not.
   */
  public boolean acceptHandGame();

  /**
   * Called when the Ai plays a handgame and has to set the contract.
   */
  public AdditionalMultipliers chooseAdditionalMultipliers();

  /**
   * Ai sets the Contract if he is declarer.
   * 
   * @return
   */
  public Contract chooseContract();


  /**
   * Ai plays a card.
   */
  public Card chooseCard();

  /*
   * Ai darf folgende Methoden aufrufen: SkatMain.mainController.localKontraAnnounced();
   * SkatMain.mainController.localRekontraAnnounced();
   * 
   */



}

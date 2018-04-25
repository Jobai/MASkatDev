package de.skat3.ai;

import de.skat3.gamelogic.AdditionalMultipliers;
import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;

public abstract class Ai {
  Player ai;

  
  public Ai(Player player) {
    this.ai = player;
  };
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

  public abstract Player getPlayer();


}


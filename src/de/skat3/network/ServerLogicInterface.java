/**
 * SKAT_3_Eclipse
 *
 * @author Jonas Bauer
 * @version 1.0 18.03.2018
 * 
 *          (c) 2018 All Rights Reserved. -------------------------
 */
package de.skat3.network;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.Contract;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Result;

/**
 * Handles all calls of the gamelogic to the server, so clients are notified. called by Logic.
 * 
 * @author Jonas Bauer
 * 
 */
public interface ServerLogicInterface {

  // Hand is in player object
  public void sendStartHandtoPlayer(Player player);

  // tells a (remote) player that he has to bid.
  public void callForBid(Player player, int biddingValue);

  // tells a (remote) player that he has to play a card
  public void callForPlay(Player player);

  // tells everyone what the contract is.
  public void broadcastContract(Contract contract);


  public void sendPlayedCard(Player player, Card card);


  public void broadcastTrickResult(Object oj);

  public void broadcastMatchResult(Object oj);


  public void callForHandOption(Player p);

  public void callForContract(Player p);

  public void sendSkat(Player p, Card[] skat);

  void broadcastRoundResult(Result result);



  /*
   * notifyLogicofPlayedCard(Player player, Card card); notifyLogicofBid (Player player, int bid);
   * notifyLogicofContract (Contract contract); notifyLogicofKontra notifyLogicofRecontra
   * 
   */


}
